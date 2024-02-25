package com.vex.services.commons.business;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CategoryDTO;
import com.vex.models.dtos.SubCategoryDTO;
import com.vex.repositories.CategoryRepository;
import com.vex.repositories.SubCategoryRepository;
import com.vex.services.commons.interfaces.CategoryService;
import com.vex.services.commons.interfaces.SubCategoryService;
import com.vex.utils.Constants;
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
            if (isInvalidInput(category)) {
                throw new ServiceException(ExceptionType.INVALID_INPUT, category.toString());
            }
            return categoryRepository.saveNewCategory(category);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_SAVING_CATEGORY, category);
        }
    }

    @Override
    public Mono<CategoryDTO> updateCategory(Integer categoryId, CategoryDTO category) throws ServiceException {
        try {
            if (isInvalidInput(category)) {
                throw new ServiceException(ExceptionType.INVALID_INPUT, category.toString());
            }
            return categoryRepository.getCategories(categoryId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.CATEGORY_NOT_FOUND, categoryId)))
                .single()
                .flatMap(c -> {
                    log.info("Updating category: {}", c.toString());
                    return categoryRepository.updateCategory(c.getCategoryId(), category);
                });
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_UPDATING_CATEGORY, categoryId, category);
        }
    }

    @Override
    public Mono<Void> deleteCategory(Integer categoryId) throws ServiceException {
        try {
            return categoryRepository.getCategories(categoryId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.CATEGORY_NOT_FOUND, categoryId)))
                .single()
                .flatMap(c -> categoryRepository.deleteCategory(c.getCategoryId()));
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
            if (isInvalidInput(subCategory)) {
                throw new ServiceException(ExceptionType.INVALID_INPUT, subCategory.toString());
            }
            return subCategoryRepository.saveNewSubCategory(subCategory);
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_SAVING_SUB_CATEGORY, subCategory.toString());
        }
    }

    @Override
    public Mono<SubCategoryDTO> updateSubCategory(Integer subCategoryId, SubCategoryDTO subCategory) throws ServiceException {
        try {
            if (isInvalidInput(subCategory)) {
                throw new ServiceException(ExceptionType.INVALID_INPUT, subCategory.toString());
            }
            return subCategoryRepository.getSubCategories(subCategoryId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.SUB_CATEGORY_NOT_FOUND, subCategoryId)))
                .single()
                .flatMap(c -> subCategoryRepository.updateSubCategory(c.getSubCategoryId(), subCategory));
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_UPDATING_SUB_CATEGORY, subCategoryId, subCategory.toString());
        }
    }

    @Override
    public Mono<Void> deleteSubCategory(Integer subCategoryId) throws ServiceException {
        try {
            return subCategoryRepository.getSubCategories(subCategoryId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.SUB_CATEGORY_NOT_FOUND, subCategoryId)))
                .single()
                .flatMap(c -> subCategoryRepository.deleteSubCategory(c.getSubCategoryId()));
        } catch (Exception e) {
            throw new ServiceException(e, ExceptionType.ERROR_DELETING_SUB_CATEGORY, subCategoryId);
        }
    }

    private boolean isInvalidInput(CategoryDTO categoryDTO) {
        if (categoryDTO.getAvailable() == null) categoryDTO.setAvailable(Constants.CHAR_Y);
        if (categoryDTO.getAvailable() != null && !categoryDTO.getAvailable().equalsIgnoreCase(Constants.CHAR_N)) {
            categoryDTO.setAvailable(Constants.CHAR_Y);
        }

        return categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isEmpty();
    }
    
    private boolean isInvalidInput(SubCategoryDTO subCategoryDTO) {
        if (subCategoryDTO.getAvailable() == null) subCategoryDTO.setAvailable(Constants.CHAR_Y);
        if (subCategoryDTO.getAvailable() != null && !subCategoryDTO.getAvailable().equalsIgnoreCase(Constants.CHAR_N)) {
            subCategoryDTO.setAvailable(Constants.CHAR_Y);
        }

        return subCategoryDTO.getSubCategoryName() == null || subCategoryDTO.getSubCategoryName().isEmpty();
    }

}
