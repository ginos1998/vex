package com.vex.controllers.commons;

import com.fasterxml.jackson.annotation.JsonView;
import com.vex.delegates.commons.interfaces.ProductDelegate;
import com.vex.models.dtos.CompanyDTO;
import com.vex.models.dtos.ProductDTO;
import com.vex.models.filters.ProductFilter;
import com.vex.utils.Views;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${app.version}/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductDelegate productDelegate;

    @PostMapping
    @Operation(summary = "Creates a new Product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product successfully created.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    public ResponseEntity<Mono<ProductDTO>> createProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productDelegate.createProduct(product));
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Updates an existing Product.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product successfully updated.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    public ResponseEntity<Mono<ProductDTO>> updateProduct(@RequestBody ProductDTO product, @PathVariable Integer productId) {
        return ResponseEntity.ok(productDelegate.updateProduct(productId, product));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Deletes an existing Product.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product successfully deleted.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    public ResponseEntity<Mono<Void>> deleteProduct(@PathVariable Integer productId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productDelegate.deleteProduct(productId));
    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get Products by branch")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products successfully returned.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)
    })
    @JsonView(Views.Public.class)
    public ResponseEntity<Flux<ProductDTO>> getProductByBranchId(@PathVariable Integer branchId, ProductFilter filter) {
        return ResponseEntity.ok(productDelegate.getProductByBranchIdAndEnabledUsingFilter(branchId, filter));
    }

}
