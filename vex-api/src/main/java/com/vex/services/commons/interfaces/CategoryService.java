package com.vex.services.commons.interfaces;

import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CategoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Flux<CategoryDTO> getCategories(Integer categoryId, String categoryName) throws ServiceException;
    Mono<CategoryDTO> saveNewCategory(CategoryDTO category) throws ServiceException;
    Mono<CategoryDTO> updateCategory(Integer categoryId, CategoryDTO category) throws ServiceException;
    Mono<Void> deleteCategory(Integer categoryId) throws ServiceException;

}
