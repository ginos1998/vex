package com.vex.repositories;

import com.vex.models.dtos.ProductDTO;
import com.vex.models.entities.Product;
import com.vex.models.filters.ProductFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> getProductById(Integer productId);
    Mono<Product> createProduct(Product product);
    Mono<Product> updateProduct(Product product);
    Mono<Void> deleteProductById(Integer productId);
    Flux<ProductDTO> getProductByBranchIdAndEnabledUsingFilter(Integer branchId, ProductFilter filter);
}
