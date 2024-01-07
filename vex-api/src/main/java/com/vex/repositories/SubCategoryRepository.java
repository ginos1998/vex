package com.vex.repositories;

import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.SubCategoryDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface SubCategoryRepository {
    Flux<SubCategoryDTO> getSubCategories(Object ...args) throws ServiceException;
    Mono<SubCategoryDTO> saveNewSubCategory(SubCategoryDTO subCategory);
    Mono<SubCategoryDTO> updateSubCategory(Integer subCategoryId, SubCategoryDTO subCategory);
    Mono<Void> deleteSubCategory(Integer subCategoryId);

}
