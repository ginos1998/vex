package com.vex.models.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.vex.models.entities.Category;
import com.vex.utils.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryDTO {
    @JsonView(Views.Public.class)
    private Integer subCategoryId;
    @JsonView(Views.Public.class)
    private Integer categoryId;
    @JsonView(Views.Public.class)
    private String subCategoryName;
    @JsonView(Views.Public.class)
    private String available;

    @Builder.Default
    @JsonView(Views.Public.class)
    private List<Category> categories = List.of();

    public static Mono<SubCategoryDTO> fromRow(List<Map<String, Object>> rows) {
        return Mono.just(SubCategoryDTO.builder()
                        .subCategoryId((Integer) rows.get(0).get("sc_sub_category_id"))
                        .subCategoryName((String) rows.get(0).get("sc_sub_category_name"))
                        .categoryId((Integer) rows.get(0).get("sc_category_id"))
                        .available((String) rows.get(0).get("sc_available"))
                        .categories(rows.stream()
                                .map(Category::fromRow)
                                .filter(Objects::nonNull)
                                .toList())
                        .build()
        );
    }
}
