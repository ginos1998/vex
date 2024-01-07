package com.vex.services.commons.business;

import com.vex.models.dtos.ProductDTO;
import com.vex.models.entities.Product;
import com.vex.repositories.ProductRepository;
import com.vex.services.commons.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class ProductServiceBusiness implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> getProductById(Long productId) {
        return null;
    }

    @Override
    public Mono<Product> createProduct(ProductDTO product) {
        return null;
    }

    @Override
    public Mono<Product> updateProduct(Long productId, ProductDTO product) {
        return null;
    }

    @Override
    public Mono<Void> deleteProduct(Long productId) {
        return null;
    }
}
