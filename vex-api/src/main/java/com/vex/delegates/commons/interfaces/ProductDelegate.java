package com.vex.delegates.commons.interfaces;

import com.vex.models.dtos.ProductDTO;
import com.vex.models.filters.ProductFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductDelegate {

    Flux<ProductDTO> getProductByBranchIdAndEnabledUsingFilter(Integer branchId, ProductFilter filter);
    Mono<ProductDTO> createProduct(ProductDTO product);
    Mono<ProductDTO> updateProduct(Integer productId, ProductDTO product);
    Mono<Void> deleteProduct(Integer productId);
}
