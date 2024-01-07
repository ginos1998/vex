package com.vex.utils;

import lombok.Getter;
import org.modelmapper.ModelMapper;

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
}
