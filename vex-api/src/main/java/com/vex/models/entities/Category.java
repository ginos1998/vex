package com.vex.models.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.vex.utils.Views;
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
    @JsonView(Views.Public.class)
    private Integer categoryId;

    @Column(value = "category_name")
    @JsonView(Views.Public.class)
    private String categoryName;

    @Column(value = "available")
    @JsonView(Views.Public.class)
    private String available;

    public static Category fromRow(Map<String, Object> row) {
        if (row.get("c_category_id") == null) {
            return null;
        }
        return Category.builder()
                .categoryId((Integer) row.get("c_category_id"))
                .categoryName((String) row.get("c_category_name"))
                .available((String) row.get("c_available"))
                .build();
    }


}