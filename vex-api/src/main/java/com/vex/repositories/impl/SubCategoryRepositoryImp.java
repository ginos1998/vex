package com.vex.repositories.impl;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.SubCategoryDTO;
import com.vex.models.entities.SubCategory;
import com.vex.repositories.SubCategoryRepository;
import com.vex.repositories.r2dbc.SubCategoryR2dbcRepository;
import com.vex.utils.DefaultMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SubCategoryRepositoryImp extends GenericRepositoryImpl implements SubCategoryRepository {
    private final SubCategoryR2dbcRepository subCategoryR2dbcRepository;

    public SubCategoryRepositoryImp(DatabaseClient databaseClient, SubCategoryR2dbcRepository subCategoryR2dbcRepository) {
        super(databaseClient);
        this.subCategoryR2dbcRepository = subCategoryR2dbcRepository;
    }

    @Override
    public Flux<SubCategoryDTO> getSubCategories(Object ...args) {
        String query = " SELECT * FROM f_get_all_sub_categories(:category_id, :sub_category_name) ";
        return databaseClient.sql(query)
                .bindValues(SubCategory.getBindValues(args))
                .fetch()
                .all()
                .bufferUntilChanged(row -> row.get("sc_sub_category_id"))
                .flatMap(SubCategoryDTO::fromRow)
                .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_GETTING_SUB_CATEGORIES, args)));
    }

    @Override
    public Mono<SubCategoryDTO> saveNewSubCategory(SubCategoryDTO subCategoryDTO) {
        return subCategoryR2dbcRepository.save(DefaultMapper.getInstance().getMapper().map(subCategoryDTO, SubCategory.class))
                .map(subCategory -> DefaultMapper.getInstance().getMapper().map(subCategory, SubCategoryDTO.class))
                .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_SAVING_SUB_CATEGORY, subCategoryDTO)));
    }

    @Override
    public Mono<SubCategoryDTO> updateSubCategory(Integer subCategoryId, SubCategoryDTO subCategoryDTO) {
        return subCategoryR2dbcRepository.findById(Long.valueOf(subCategoryId))
                .flatMap(c -> {
                    SubCategory mappedSubCategory = DefaultMapper.getInstance()
                            .getMapper()
                            .map(subCategoryDTO, SubCategory.class);
                    return subCategoryR2dbcRepository.save(mappedSubCategory)
                            .map(subCategory1 -> DefaultMapper.getInstance().getMapper()
                                    .map(subCategory1, SubCategoryDTO.class));
                })
                .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_UPDATING_SUB_CATEGORY, subCategoryId, subCategoryDTO.toString())));

    }

    @Override
    public Mono<Void> deleteSubCategory(Integer subCategoryId) {
        return subCategoryR2dbcRepository.deleteById(Long.valueOf(subCategoryId))
                .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_DELETING_SUB_CATEGORY, subCategoryId)));
    }

}
