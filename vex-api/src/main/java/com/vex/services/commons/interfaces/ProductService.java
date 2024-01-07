package com.vex.services.commons.interfaces;

import com.vex.models.dtos.ProductDTO;
import com.vex.models.entities.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> getAllProducts();
    Mono<Product> getProductById(Long productId);
    Mono<Product> createProduct(ProductDTO product);
    Mono<Product> updateProduct(Long productId, ProductDTO product);
    Mono<Void> deleteProduct(Long productId);

}
