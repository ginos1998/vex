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
@Table("sub_category")
public class SubCategory {

    @Id
    @Column(value = "sub_category_id")
    @JsonView(Views.Public.class)
    private Integer subCategoryId;

    @Column(value = "category_id")
    private Integer categoryId;

    @Column(value = "sub_category_name")
    @JsonView(Views.Public.class)
    private String subCategoryName;

    @Column(value = "available")
    @JsonView(Views.Public.class)
    private String available;

    public static Map<String, Object> getBindValues(Object ...args) {
        return Map.of(
                "category_id", args[0] == null ? 0 : args[0],
                "sub_category_name", args[1] == null ? "" : args[1]
        );
    }

}
