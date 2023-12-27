package com.vex.repositories.impl;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CategoryDTO;
import com.vex.models.entities.Category;
import com.vex.repositories.CategoryRepository;
import com.vex.repositories.r2dbc.CategoryR2dbcRepository;
import com.vex.utils.DefaultMapper;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryRepositoryImpl extends GenericRepositoryImpl implements CategoryRepository {
    private final CategoryR2dbcRepository categoryR2dbcRepository;

    public CategoryRepositoryImpl(DatabaseClient databaseClient, CategoryR2dbcRepository categoryR2dbcRepository) {
        super(databaseClient);
        this.categoryR2dbcRepository = categoryR2dbcRepository;
    }

    public Flux<CategoryDTO> getCategories(Object ...args) {
        String query = " SELECT * FROM f_get_all_categories(:category_id, :category_name) ";
        return databaseClient.sql(query)
                .bindValues(Category.getBindValues(args))
                .fetch()
                .all()
                .bufferUntilChanged(row -> row.get("c_category_id"))
                .flatMap(CategoryDTO::fromRows)
                .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_GETTING_CATEGORIES, args)));
    }

    @Override
    public Mono<CategoryDTO> saveNewCategory(CategoryDTO category) {
        return categoryR2dbcRepository.save(DefaultMapper.getInstance().getMapper().map(category, Category.class))
                .map(c -> DefaultMapper.getInstance().getMapper().map(c, CategoryDTO.class))
                .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_SAVING_CATEGORY, category.toString())));
    }

    @Override
    public Mono<CategoryDTO> updateCategory(Integer categoryId, CategoryDTO category) {
        return categoryR2dbcRepository.findById(categoryId)
                .flatMap(catDTO -> {
                    catDTO = DefaultMapper.getInstance().getMapper().map(category, Category.class);
                    return categoryR2dbcRepository.save(catDTO)
                            .map(cat -> DefaultMapper.getInstance().getMapper().map(cat, CategoryDTO.class));
                })
                .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_UPDATING_CATEGORY, categoryId, category.toString())));
    }

    @Override
    public Mono<Void> deleteCategory(Integer categoryId) {
        return categoryR2dbcRepository.findById(categoryId)
                .flatMap(categoryR2dbcRepository::delete)
                .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_UPDATING_CATEGORY, categoryId)));
    }
}
