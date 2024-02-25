package com.vex.repositories;

import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CategoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository {
    Flux<CategoryDTO> getCategories(Object ...args);
    Mono<CategoryDTO> saveNewCategory(CategoryDTO category) throws ServiceException;
    Mono<CategoryDTO> updateCategory(Integer categoryId, CategoryDTO category);
    Mono<Void> deleteCategory(Integer categoryId);
}
