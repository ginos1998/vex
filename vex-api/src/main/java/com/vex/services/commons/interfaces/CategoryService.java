package com.vex.services.commons.interfaces;

import com.vex.models.dtos.CategoryDTO;
import com.vex.models.entities.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Flux<Category> getAllCategories();
    Mono<Category> getCategoryById(Long categoryId);
    Mono<Category> saveNewCategory(CategoryDTO category);
    Mono<Category> updateCategory(Long categoryId, CategoryDTO category);
    Mono<Void> deleteCategory(Long categoryId);

}
