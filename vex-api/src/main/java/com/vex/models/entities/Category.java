package com.vex.models.entities;

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
        return Map.of(
                "category_id", args[0] == null ? 0 : args[0],
                "category_name", args[1] == null ? "" : args[1]
        );
    }


}