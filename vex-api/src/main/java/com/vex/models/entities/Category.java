package com.vex.models.entities;

import com.vex.utils.DefaultMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("category")
public class Category {

    @Id
    @Column(value = "category_id")
    private Integer categoryId;

    @Column(value = "category_name")
    private String categoryName;

    @Column(value = "available")
    private String available;

    public static Map<String, Object> getBindValues(Object ...args) {
        Map<Integer, Map<String, Object>> defaultValues = Map.of(
                0, Map.of("category_id", 0),
                1, Map.of( "category_name", "")
        );
        return DefaultMapper.getInstance().getBindValues(defaultValues, args);
    }


}