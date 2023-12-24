package com.vex.services.commons.business;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CategoryDTO;
import com.vex.models.dtos.SubCategoryDTO;
import com.vex.models.entities.Category;
import com.vex.repositories.CategoryRepository;
import com.vex.repositories.SubCategoryRepository;
import com.vex.services.commons.interfaces.CategoryService;
import com.vex.services.commons.interfaces.SubCategoryService;
import com.vex.utils.DefaultMapper;
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
    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Mono<Category> saveNewCategory(CategoryDTO category) {
        return categoryRepository.save(DefaultMapper.getInstance()
                .getMapper()
                .map(category, Category.class));
    }

    @Override
    public Mono<Category> updateCategory(Long categoryId, CategoryDTO category) {
        return categoryRepository.findById(categoryId)
                .flatMap(c -> {
                    Category mappedCategory = DefaultMapper.getInstance()
                            .getMapper()
                            .map(category, Category.class);
                    return categoryRepository.save(mappedCategory);
                });
    }

    @Override
    public Mono<Void> deleteCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .flatMap(categoryRepository::delete);
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
