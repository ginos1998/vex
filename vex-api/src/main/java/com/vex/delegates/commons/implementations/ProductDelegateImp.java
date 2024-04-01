package com.vex.delegates.commons.implementations;

import com.vex.delegates.commons.interfaces.ProductDelegate;
import com.vex.models.dtos.ProductDTO;
import com.vex.models.filters.ProductFilter;
import com.vex.services.commons.interfaces.BranchService;
import com.vex.services.commons.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductDelegateImp implements ProductDelegate {

    private final BranchService branchService;
    private final ProductService productService;

    @Override
    public Flux<ProductDTO> getProductByBranchIdAndEnabledUsingFilter(Integer branchId, ProductFilter filter) {
        return branchService.getBranchById(branchId)
            .flatMapMany(
                branch -> productService.getProductByBranchIdAndEnabledUsingFilter(branch.getBranchId(), filter)
            );
    }

    @Override
    public Mono<ProductDTO> createProduct(ProductDTO product) {
        return productService.createProduct(product);
    }

    @Override
    public Mono<ProductDTO> updateProduct(Integer productId, ProductDTO product) {
        return productService.updateProduct(productId, product);
    }

    @Override
    public Mono<Void> deleteProduct(Integer productId) {
        return productService.deleteProduct(productId);
    }
}
