package com.vex.controllers.commons;

import com.fasterxml.jackson.annotation.JsonView;
import com.vex.delegates.commons.interfaces.CategoryDelegate;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.SubCategoryDTO;
import com.vex.models.entities.SubCategory;
import com.vex.utils.Views;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${app.version}/category")
@Api(value = "Category Controller", tags = "Category Controller", produces = "application/json", consumes = "application/json")
@Primary
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryDelegate categoryDelegate;

    @GetMapping("/sub-categories")
    @ApiOperation(value = "Returns a list of sub-categories filtering by categoryId or subCategoryName, or both. If no parameters are passed, returns all sub-categories.",
            response = SubCategory.class, responseContainer = "dto", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List successfully returned."),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 409, message = "Conflict."),
            @ApiResponse(code = 500, message = "Internal server error.")

    })
    @JsonView(Views.Public.class)
    public Flux<?> getSubCategories(@RequestParam(required = false) Integer categoryId,
                                                 @RequestParam(required = false) String subCategoryName) throws ServiceException {
        return categoryDelegate.getSubCategories(categoryId, subCategoryName);
    }

    @PostMapping("/sub-categories")
    @ApiOperation(value = "Saves a new sub category",
            response = SubCategory.class, responseContainer = "dto", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created."),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 409, message = "Conflict."),
            @ApiResponse(code = 500, message = "Internal server error.")

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<SubCategoryDTO>> saveNewSubCategory(@RequestBody SubCategoryDTO subCategory) throws ServiceException {
        return categoryDelegate.saveNewSubCategory(subCategory)
                .flatMap(subCategoryDTO -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(subCategoryDTO)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/sub-categories/{subCategoryId}")
    @ApiOperation(value = "Updates a sub category",
            response = SubCategory.class, responseContainer = "dto", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated."),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 409, message = "Conflict."),
            @ApiResponse(code = 500, message = "Internal server error.")

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<SubCategoryDTO>> updateSubCategory(@PathVariable Integer subCategoryId,
                                     @RequestBody SubCategoryDTO subCategory) throws ServiceException {
        return categoryDelegate.updateSubCategory(subCategoryId, subCategory)
                .flatMap(subCategoryDTO -> Mono.just(ResponseEntity.status(HttpStatus.CREATED.value()).body(subCategoryDTO)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping("/sub-categories/{subCategoryId}")
    @ApiOperation(value = "Deletes a sub category",
            response = SubCategory.class, responseContainer = "dto", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 409, message = "Conflict."),
            @ApiResponse(code = 500, message = "Internal server error.")

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<Void>> deleteSubCategory(@PathVariable Integer subCategoryId) throws ServiceException {
        return categoryDelegate.deleteSubCategory(subCategoryId)
                .then(Mono.defer(() -> Mono.just(ResponseEntity.status(HttpStatus.OK).build())));
    }




}
