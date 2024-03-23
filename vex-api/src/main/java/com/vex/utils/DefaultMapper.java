package com.vex.utils;

import lombok.Getter;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class DefaultMapper {
    private final ModelMapper mapper;
    private static DefaultMapper instance;

    private DefaultMapper() {
        this.mapper = new ModelMapper();
    }

    public static DefaultMapper getInstance() {
        if (instance == null) {
            instance = new DefaultMapper();
        }
        return instance;
    }

    public void mapNonNull(Object source, Object destinationType) {
        Condition<Object, Object> notNullCondition = context -> context.getSource() != null;
        Converter<Object, Object> notNullConverter = context -> context.getSource() == null ? context.getDestination() : context.getSource();
        mapper.getConfiguration().setPropertyCondition(notNullCondition);
        mapper.addConverter(notNullConverter);
        mapper.map(source, destinationType);
    }

    // define a wrapper method for the ModelMapper.map() method
    public <D> D map(Object source, Class<D> destinationType) {
        return mapper.map(source, destinationType);
    }

    public Map<String, Object> getBindValues(Map<Integer, Map<String, Object>> defaultValues, Object ...args) {
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < defaultValues.size(); i++) {
            if (i >= args.length) {
                map.put(defaultValues.get(i).keySet().iterator().next(),
                        defaultValues.get(i).values().iterator().next());
            } else {
                map.put(defaultValues.get(i).keySet().iterator().next(),
                        args[i] == null ? defaultValues.get(i).values().iterator().next() : args[i]);
            }
        }

        return map;
    }
}
