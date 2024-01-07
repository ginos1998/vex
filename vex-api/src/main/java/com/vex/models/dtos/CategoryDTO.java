package com.vex.models.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.vex.utils.Views;
import lombok.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    @JsonView(Views.Public.class)
    private Integer categoryId;
    @JsonView(Views.Public.class)
    private String categoryName;
    @JsonView(Views.Public.class)
    private String available;

    public static Mono<CategoryDTO> fromRows(List<Map<String, Object>> rows) {
        if (rows.get(0).get("c_category_id") == null) {
            return Mono.empty();
        }

        return Mono.just(CategoryDTO.builder()
                .categoryId((Integer) rows.get(0).get("c_category_id"))
                .categoryName((String) rows.get(0).get("c_category_name"))
                .available((String) rows.get(0).get("c_available"))
                .build()
        );
    }

    public static CategoryDTO fromRow(Map<String, Object> row) {
        return Objects.requireNonNull(fromRows(List.of(row))).block();
    }
}
