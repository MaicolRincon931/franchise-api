package com.accenture.franchise_api.presentation.controller;

import com.accenture.franchise_api.application.usecase.AddBranchUseCase;
import com.accenture.franchise_api.application.usecase.UpdateBranchNameUseCase;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.presentation.dto.CreateBranchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Tag(name = "Branch", description = "Endpoints for managing branches")
public class BranchController {

    private final AddBranchUseCase addBranchUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;

    @PostMapping("/franchises/{franchiseId}/branches")
    @Operation(summary = "Add a new branch to a franchise")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Branch added successfully"),
            @ApiResponse(responseCode = "404", description = "Franchise not found")
    })
    public Mono<Franchise> addBranch(@PathVariable String franchiseId,
            @Valid @RequestBody CreateBranchRequest request) {
        return addBranchUseCase.execute(franchiseId, request.getName());
    }

    @PutMapping("/branches/{branchId}/name")
    @Operation(summary = "Update branch name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Branch name updated successfully"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    public Mono<Franchise> updateBranchName(@PathVariable String branchId,
            @Valid @RequestBody CreateBranchRequest request) {
        return updateBranchNameUseCase.execute(branchId, request.getName());
    }
}
