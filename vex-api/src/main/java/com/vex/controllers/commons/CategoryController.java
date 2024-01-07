package com.vex.controllers.commons;

import com.fasterxml.jackson.annotation.JsonView;
import com.vex.delegates.commons.interfaces.CategoryDelegate;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CategoryDTO;
import com.vex.models.dtos.SubCategoryDTO;
import com.vex.utils.Views;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${app.version}/category")
@ApiOperation(value = "Category API", tags = "Category")
@Primary
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryDelegate categoryDelegate;

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("Ok");
    }

    @GetMapping()
    @Operation(summary = "Returns a list of categories filtering by categoryId or subCategoryName, or both. If no parameters are passed, returns all sub-categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List successfully returned.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No content.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Flux<?> getCategories(@RequestParam(required = false) Integer categoryId,
                                    @RequestParam(required = false) String categoryName) throws ServiceException {
        return categoryDelegate.getCategories(categoryId, categoryName);
    }

    @PostMapping()
    @Operation(summary = "Saves a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created."),
            @ApiResponse(responseCode = "204", description = "No content.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<CategoryDTO>> saveNewCategory(@RequestBody CategoryDTO subCategory) throws ServiceException {
        return categoryDelegate.saveNewCategory(subCategory)
                .flatMap(categoryDTO -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Updates a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated."),
            @ApiResponse(responseCode = "204", description = "No content.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<CategoryDTO>> updateCategory(@PathVariable Integer categoryId,
                                                                  @RequestBody CategoryDTO subCategory) throws ServiceException {
        return categoryDelegate.updateCategory(categoryId, subCategory)
                .flatMap(categoryDTO -> Mono.just(ResponseEntity.status(HttpStatus.CREATED.value()).body(categoryDTO)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Deletes a sub category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<Void>> deleteCategory(@PathVariable Integer categoryId) throws ServiceException {
        return categoryDelegate.deleteCategory(categoryId)
                .then(Mono.defer(() -> Mono.just(ResponseEntity.status(HttpStatus.OK).build())));
    }

    @GetMapping("/sub-categories")
    @Operation(summary = "Returns a list of sub-categories filtering by categoryId or subCategoryName, or both. If no parameters are passed, returns all sub-categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List successfully returned.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No content.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Flux<?> getSubCategories(@RequestParam(required = false) Integer categoryId,
                                                 @RequestParam(required = false) String subCategoryName) throws ServiceException {
        return categoryDelegate.getSubCategories(categoryId, subCategoryName);
    }

    @PostMapping("/sub-categories")
    @Operation(summary = "Saves a new sub category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created."),
            @ApiResponse(responseCode = "204", description = "No content.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<SubCategoryDTO>> saveNewSubCategory(@RequestBody SubCategoryDTO subCategory) throws ServiceException {
        return categoryDelegate.saveNewSubCategory(subCategory)
                .flatMap(subCategoryDTO -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(subCategoryDTO)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/sub-categories/{subCategoryId}")
    @Operation(summary = "Updates a sub category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated."),
            @ApiResponse(responseCode = "204", description = "No content.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<SubCategoryDTO>> updateSubCategory(@PathVariable Integer subCategoryId,
                                     @RequestBody SubCategoryDTO subCategory) throws ServiceException {
        return categoryDelegate.updateSubCategory(subCategoryId, subCategory)
                .flatMap(subCategoryDTO -> Mono.just(ResponseEntity.status(HttpStatus.CREATED.value()).body(subCategoryDTO)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping("/sub-categories/{subCategoryId}")
    @Operation(summary = "Deletes a sub category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<Void>> deleteSubCategory(@PathVariable Integer subCategoryId) throws ServiceException {
        return categoryDelegate.deleteSubCategory(subCategoryId)
                .then(Mono.defer(() -> Mono.just(ResponseEntity.status(HttpStatus.OK).build())));
    }

}
