package com.vex.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private String categoryName;
    private char available;
    private SubCategoryDTO subCategoryDTO;
}
