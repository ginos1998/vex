package com.vex.controllers.commons;

import com.vex.delegates.commons.interfaces.BranchDelegate;
import com.vex.exceptions.ServiceException;
import com.vex.models.dtos.BranchDTO;
import com.vex.utils.JwtParam;
import com.vex.utils.JwtValues;
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
@RequestMapping("${app.version}/branch")
@RequiredArgsConstructor
public class BranchController {
    private final BranchDelegate branchDelegate;

    @PostMapping
    @Operation(summary = "Creates a new Branch.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Branch successfully created.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class)) }),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    public Mono<ResponseEntity<BranchDTO>> createBranch(@RequestBody BranchDTO branchDTO,
                                                        @RequestHeader("Authorization") String token) throws ServiceException {
        String username = JwtValues.getParam(JwtParam.USERNAME, token);
        return branchDelegate.createBranch(branchDTO, username)
            .map(b -> ResponseEntity.status(HttpStatus.CREATED).body(b));
    }

    @PutMapping("/{branchId}")
    @Operation(summary = "Updates a Branch.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Branch successfully updated.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class)) }),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    public Mono<ResponseEntity<BranchDTO>> updateBranch(@PathVariable Integer branchId, @RequestBody BranchDTO branchDTO) throws ServiceException {
        return branchDelegate.updateBranch(branchId, branchDTO)
            .map(b -> ResponseEntity.status(HttpStatus.OK).body(b));
    }

    @GetMapping("/{branchId}")
    @Operation(summary = "Returns a Branch by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Branch successfully returned.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class)) }),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    public Mono<ResponseEntity<BranchDTO>> getBranchById(@PathVariable Integer branchId) throws ServiceException {
        return branchDelegate.getBranchById(branchId)
            .map(b -> ResponseEntity.status(HttpStatus.OK).body(b));
    }

    @DeleteMapping("/{branchId}")
    @Operation(summary = "Deletes a Branch by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Branch successfully deleted.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class)) }),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    public Mono<ResponseEntity<Void>> deleteBranch(@PathVariable Integer branchId) throws ServiceException {
        return branchDelegate.deleteBranch(branchId)
            .then(Mono.fromCallable(
                () -> ResponseEntity.status(HttpStatus.NO_CONTENT).build())
            );
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Returns all Branches from a Company.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Branches successfully returned.",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BranchDTO.class)) }),
        @ApiResponse(responseCode = "409", description = "Conflict.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content)

    })
    public Flux<ResponseEntity<BranchDTO>> findBranchesByCompanyId(@PathVariable Integer companyId) throws ServiceException {
        return branchDelegate.findBranchesByCompanyId(companyId)
            .map(b -> ResponseEntity.status(HttpStatus.OK).body(b));
    }
}
