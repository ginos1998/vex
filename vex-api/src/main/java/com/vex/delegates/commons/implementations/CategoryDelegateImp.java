package com.vex.delegates.commons.implementations;

import com.vex.delegates.commons.interfaces.CategoryDelegate;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.SubCategoryDTO;
import com.vex.services.commons.interfaces.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Primary
@RequiredArgsConstructor
public class CategoryDelegateImp implements CategoryDelegate {

    private final SubCategoryService subCategoryService;

    @Override
    public Flux<SubCategoryDTO> getSubCategories(Integer categoryId, String subCategoryName) throws ServiceException {
        return subCategoryService.getSubCategories(categoryId, subCategoryName);
    }

    @Override
    public Mono<SubCategoryDTO> saveNewSubCategory(SubCategoryDTO subCategory) throws ServiceException {
        return subCategoryService.saveNewSubCategory(subCategory);
    }

    @Override
    public Mono<SubCategoryDTO> updateSubCategory(Integer subCategoryId, SubCategoryDTO subCategory) throws ServiceException {
        return subCategoryService.updateSubCategory(subCategoryId, subCategory);
    }

    @Override
    public Mono<Void> deleteSubCategory(Integer subCategoryId) throws ServiceException {
        return subCategoryService.deleteSubCategory(subCategoryId);
    }
}
