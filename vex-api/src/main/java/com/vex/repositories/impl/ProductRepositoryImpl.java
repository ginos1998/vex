package com.vex.repositories.impl;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.ProductDTO;
import com.vex.models.entities.Product;
import com.vex.models.filters.ProductFilter;
import com.vex.repositories.ProductRepository;
import com.vex.repositories.r2dbc.ProductR2dbcRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductRepositoryImpl extends GenericRepositoryImpl implements ProductRepository {

    private final ProductR2dbcRepository productR2dbcRepository;

    public ProductRepositoryImpl(DatabaseClient databaseClient, ProductR2dbcRepository productR2dbcRepository) {
        super(databaseClient);
        this.productR2dbcRepository = productR2dbcRepository;
    }

    @Override
    public Mono<Product> getProductById(Integer productId) {
        return productR2dbcRepository.findById(productId)
            .doOnError(error -> {
                log.error(String.format(ExceptionType.ERROR_GETTING_PRODUCT.getDescription(), productId), error);
                throw new ServiceException(error, ExceptionType.ERROR_GETTING_PRODUCT, productId);
            });
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return productR2dbcRepository.save(product)
            .doOnError(error -> {
                log.error(String.format(ExceptionType.ERROR_CREATING_PRODUCT.getDescription(), product), error);
                throw new ServiceException(error, ExceptionType.ERROR_CREATING_PRODUCT, product.getName());
            });
    }

    @Override
    public Mono<Product> updateProduct(Product product) {
        return productR2dbcRepository.save(product)
            .doOnError(error -> {
                log.error(String.format(ExceptionType.ERROR_UPDATING_PRODUCT.getDescription(), product), error);
                throw new ServiceException(error, ExceptionType.ERROR_UPDATING_PRODUCT, product.getName());
            });
    }

    @Override
    public Mono<Void> deleteProductById(Integer productId) {
        return productR2dbcRepository.deleteById(productId)
            .doOnError(error -> {
                log.error(String.format(ExceptionType.ERROR_DELETING_PRODUCT.getDescription(), productId), error);
                throw new ServiceException(error, ExceptionType.ERROR_DELETING_PRODUCT, productId);
            });
    }

    @Override
    public Flux<ProductDTO> getProductByBranchIdAndEnabledUsingFilter(Integer branchId, ProductFilter filter) {
        String query = " SELECT * FROM f_get_product_by_branch_using_filter("+filter.getQueryParams()+") ";
        return databaseClient.sql(query)
            .bindValues(filter.getBindValues())
            .fetch()
            .all()
            .bufferUntilChanged(row -> row.get("p_product_id"))
            .flatMap(ProductDTO::fromRow)
            .onErrorResume(e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_GETTING_PRODUCTS_BY_BRANCH_WITH_FILTER, filter.toString())));
    }

}
