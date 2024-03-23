package com.vex.controllers.commons;

import com.fasterxml.jackson.annotation.JsonView;
import com.vex.delegates.commons.interfaces.CompanyDelegate;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.CompanyDTO;
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
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("${app.version}/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyDelegate companyDelegate;

    @PostMapping
    @Operation(summary = "Creates a new Company.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company successfully created.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDTO.class)) }),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<CompanyDTO>> addCompany(@RequestBody CompanyDTO company) throws ServiceException {
        return companyDelegate.addCompany(company)
            .flatMap(c -> Mono.just(ResponseEntity.ok(c)));
    }

    @PutMapping("/{companyId}")
    @Operation(summary = "Updates an existing Company.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Company successfully updated.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<CompanyDTO>> updateCompany(@RequestBody CompanyDTO company, @PathVariable Integer companyId) throws ServiceException {
        return companyDelegate.updateCompany(company, companyId)
            .flatMap(c -> Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(c)));
    }

    @DeleteMapping("/{companyId}")
    @Operation(summary = "Deletes an existing Company.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Company successfully deleted.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    public Mono<ResponseEntity<Void>> deleteCompanyById(@PathVariable Integer companyId) throws ServiceException {
        return companyDelegate.deleteCompanyById(companyId)
            .then(Mono.defer(
                () -> Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()))
            );
    }

    @GetMapping("/{companyId}")
    @Operation(summary = "Get company by id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Company successfully returned.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Not found.", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<CompanyDTO>> getCompanyById(@PathVariable Integer companyId) throws ServiceException {
        return companyDelegate.getCompanyById(companyId)
            .flatMap(c -> Mono.just(ResponseEntity.ok(c)));
    }

}
