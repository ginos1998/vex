package com.vex.services.commons.business;

import com.vex.exceptions.ExceptionType;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.ProductDTO;
import com.vex.models.entities.Product;
import com.vex.models.filters.ProductFilter;
import com.vex.repositories.ProductRepository;
import com.vex.services.commons.interfaces.ProductService;
import com.vex.utils.CommonFunctions;
import com.vex.utils.Constants;
import com.vex.utils.DefaultMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class ProductServiceBusiness implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Flux<ProductDTO> getProductByBranchIdAndEnabledUsingFilter(Integer branchId, ProductFilter filter) {
        return productRepository.getProductByBranchIdAndEnabledUsingFilter(branchId, filter)
            .onErrorResume(
                e -> Mono.error(new ServiceException(e, ExceptionType.ERROR_GETTING_PRODUCTS_BY_BRANCH_WITH_FILTER, filter.toString()))
            );
    }

    @Override
    public Mono<ProductDTO> createProduct(ProductDTO product) {
        if (isInvalidInput(product)) {
            return Mono.error(new ServiceException(ExceptionType.INVALID_INPUT));
        }
        Product newProduct = DefaultMapper.getInstance().map(product, Product.class);
        return productRepository.createProduct(newProduct)
            .map(productCreated -> DefaultMapper.getInstance().map(productCreated, ProductDTO.class))
            .onErrorResume(
                error -> {
                    log.error(String.format(ExceptionType.ERROR_CREATING_PRODUCT.getDescription(), product));
                    return Mono.error(new ServiceException(error, ExceptionType.ERROR_CREATING_PRODUCT, product.getName()));
                }
            );
    }

    @Override
    public Mono<ProductDTO> updateProduct(Integer productId, ProductDTO product) {
        return productRepository.getProductById(productId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.PRODUCT_NOT_FOUND, productId)))
            .flatMap(productFound -> {
                DefaultMapper.getInstance().mapNonNull(productFound, product);
                return productRepository.updateProduct(productFound)
                    .map(productUpdated -> DefaultMapper.getInstance().map(productUpdated, ProductDTO.class));
            })
            .onErrorResume(
                error -> {
                    log.error(String.format(ExceptionType.ERROR_UPDATING_PRODUCT.getDescription(), product));
                    return Mono.error(new ServiceException(error, ExceptionType.ERROR_UPDATING_PRODUCT, product.getName()));
                }
            );
    }

    @Override
    public Mono<Void> deleteProduct(Integer productId) {
        return productRepository.getProductById(productId)
                .switchIfEmpty(Mono.error(new ServiceException(ExceptionType.PRODUCT_NOT_FOUND, productId)))
                .flatMap(product -> productRepository.deleteProductById(productId))
                .onErrorResume(
                    error -> {
                        log.error(String.format(ExceptionType.ERROR_DELETING_PRODUCT.getDescription(), productId));
                        return Mono.error(new ServiceException(error, ExceptionType.ERROR_DELETING_PRODUCT, productId));
                    }
                );
    }

    private boolean isInvalidInput(ProductDTO product) {
        if (product == null) return true;
        if (product.getCreatedAt() == null) product.setCreatedAt(LocalDateTime.now());
        if (product.getUpdatedAt() == null) product.setUpdatedAt(LocalDateTime.now());
        if (product.getEnabled() == null) product.setEnabled(Constants.CHAR_Y);

        return CommonFunctions.isNullOrEmpty(product.getName())
            || product.getPrice() == null || product.getBranchId() == null || product.getCategoryId() == null
            || product.getBuyPrice() == null || product.getStock() == null || product.getIvaRate() == null
            || product.getSupplierId() == null || product.getBrandId() == null
            || CommonFunctions.isNullOrEmpty(product.getCode());
    }
}
