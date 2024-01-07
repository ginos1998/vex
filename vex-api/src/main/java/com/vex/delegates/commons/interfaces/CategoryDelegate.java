package com.vex.delegates.commons.interfaces;

import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CategoryDTO;
import com.vex.models.dtos.SubCategoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryDelegate {
    Flux<SubCategoryDTO> getSubCategories(Integer categoryId, String subCategoryName) throws ServiceException;
    Mono<SubCategoryDTO> saveNewSubCategory(SubCategoryDTO subCategory) throws ServiceException;
    Mono<SubCategoryDTO> updateSubCategory(Integer subCategoryId, SubCategoryDTO subCategory) throws ServiceException;
    Mono<Void> deleteSubCategory(Integer subCategoryId) throws ServiceException;

    Flux<CategoryDTO> getCategories(Integer categoryId, String categoryName) throws ServiceException;
    Mono<CategoryDTO> saveNewCategory(CategoryDTO category) throws ServiceException;
    Mono<CategoryDTO> updateCategory(Integer categoryId, CategoryDTO category) throws ServiceException;
    Mono<Void> deleteCategory(Integer categoryId) throws ServiceException;
}
