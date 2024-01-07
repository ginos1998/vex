package com.vex.services.commons.business;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CategoryDTO;
import com.vex.models.dtos.SubCategoryDTO;
import com.vex.repositories.CategoryRepository;
import com.vex.repositories.SubCategoryRepository;
import com.vex.services.commons.interfaces.CategoryService;
import com.vex.services.commons.interfaces.SubCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceBusiness implements CategoryService, SubCategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public Flux<CategoryDTO> getCategories(Integer categoryId, String categoryName) throws ServiceException {
        try {
            return categoryRepository.getCategories(categoryId, categoryName);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_GETTING_CATEGORIES);
        }
    }

    @Override
    public Mono<CategoryDTO> saveNewCategory(CategoryDTO category) throws ServiceException {
        try {
            return categoryRepository.saveNewCategory(category);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_SAVING_CATEGORY, category);
        }
    }

    @Override
    public Mono<CategoryDTO> updateCategory(Integer categoryId, CategoryDTO category) throws ServiceException {
        try {
            return categoryRepository.updateCategory(categoryId, category);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_UPDATING_CATEGORY, categoryId, category);
        }
    }

    @Override
    public Mono<Void> deleteCategory(Integer categoryId) throws ServiceException {
        try {
            return categoryRepository.deleteCategory(categoryId);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_DELETING_CATEGORY, categoryId);
        }
    }

    @Override
    public Flux<SubCategoryDTO> getSubCategories(Integer categoryId, String subCategoryName) throws ServiceException {
        try {
            return subCategoryRepository.getSubCategories(categoryId, subCategoryName);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_GETTING_SUB_CATEGORIES, categoryId, subCategoryName);
        }
    }

    @Override
    public Mono<SubCategoryDTO> getSubCategoryById(Integer subCategoryId) throws ServiceException {
        try {
            return subCategoryRepository.getSubCategories(subCategoryId)
                    .singleOrEmpty();
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_GETTING_SUB_CATEGORIES, subCategoryId);
        }
    }

    @Override
    public Mono<SubCategoryDTO> saveNewSubCategory(SubCategoryDTO subCategory) throws ServiceException {
        try {
            return subCategoryRepository.saveNewSubCategory(subCategory);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_SAVING_SUB_CATEGORY, subCategory.toString());
        }
    }

    @Override
    public Mono<SubCategoryDTO> updateSubCategory(Integer subCategoryId, SubCategoryDTO subCategory) throws ServiceException {
        try {
            return subCategoryRepository.updateSubCategory(subCategoryId, subCategory);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_UPDATING_SUB_CATEGORY, subCategoryId, subCategory.toString());
        }
    }

    @Override
    public Mono<Void> deleteSubCategory(Integer subCategoryId) throws ServiceException {
        try {
            return subCategoryRepository.deleteSubCategory(subCategoryId);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_DELETING_SUB_CATEGORY, subCategoryId);
        }
    }


}
