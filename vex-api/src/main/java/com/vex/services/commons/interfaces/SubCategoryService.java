package com.vex.services.commons.interfaces;

import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.SubCategoryDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface SubCategoryService {
    Flux<SubCategoryDTO> getSubCategories(Integer categoryId, String subCategoryName) throws ServiceException;
    Mono<SubCategoryDTO> getSubCategoryById(Integer subCategoryId) throws ServiceException;
    Mono<SubCategoryDTO> saveNewSubCategory(SubCategoryDTO subCategory) throws ServiceException;
    Mono<SubCategoryDTO> updateSubCategory(Integer subCategoryId, SubCategoryDTO subCategory) throws ServiceException;
    Mono<Void> deleteSubCategory(Integer subCategoryId) throws ServiceException;

}
