package com.vex.delegates.commons.interfaces;

import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.SubCategoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryDelegate {
    Flux<SubCategoryDTO> getSubCategories(Integer categoryId, String subCategoryName) throws ServiceException;
    Mono<SubCategoryDTO> saveNewSubCategory(SubCategoryDTO subCategory) throws ServiceException;
    Mono<SubCategoryDTO> updateSubCategory(Integer subCategoryId, SubCategoryDTO subCategory) throws ServiceException;
    Mono<Void> deleteSubCategory(Integer subCategoryId) throws ServiceException;

}
