package com.accenture.franchise_api.presentation.controller;

import com.accenture.franchise_api.application.usecase.CreateFranchiseUseCase;
import com.accenture.franchise_api.application.usecase.GetTopProductsUseCase;
import com.accenture.franchise_api.application.usecase.UpdateFranchiseNameUseCase;
import com.accenture.franchise_api.domain.dto.BranchTopProductResponse;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.presentation.dto.CreateFranchiseRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Tag(name = "Franchise", description = "Endpoints for managing franchises")
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final GetTopProductsUseCase getTopProductsUseCase;

    @PostMapping("/franchises")
    @Operation(summary = "Create a new franchise")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Franchise created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Mono<Franchise> createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        return createFranchiseUseCase.execute(request.getName());
    }

    @PutMapping("/franchises/{id}/name")
    @Operation(summary = "Update franchise name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Franchise name updated successfully"),
            @ApiResponse(responseCode = "404", description = "Franchise not found")
    })
    public Mono<Franchise> updateFranchiseName(@PathVariable String id,
            @Valid @RequestBody CreateFranchiseRequest request) {
        return updateFranchiseNameUseCase.execute(id, request.getName());
    }

    @GetMapping("/franchises/{franchiseId}/top-products")
    @Operation(summary = "Get product with top stock per branch in a franchise")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of top products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Franchise not found")
    })
    public Flux<BranchTopProductResponse> getTopProducts(@PathVariable String franchiseId) {
        return getTopProductsUseCase.execute(franchiseId);
    }
}
