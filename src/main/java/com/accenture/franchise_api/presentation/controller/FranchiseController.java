package com.accenture.franchise_api.presentation.controller;

import com.accenture.franchise_api.application.usecase.CreateFranchiseUseCase;
import com.accenture.franchise_api.application.usecase.GetTopProductsUseCase;
import com.accenture.franchise_api.application.usecase.UpdateFranchiseNameUseCase;
import com.accenture.franchise_api.domain.dto.BranchTopProductResponse;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.presentation.dto.CreateFranchiseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final GetTopProductsUseCase getTopProductsUseCase;

    @PostMapping("/franchises")
    public Mono<Franchise> createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        return createFranchiseUseCase.execute(request.getName());
    }

    @PutMapping("/franchises/{id}/name")
    public Mono<Franchise> updateFranchiseName(@PathVariable String id,
            @Valid @RequestBody CreateFranchiseRequest request) {
        return updateFranchiseNameUseCase.execute(id, request.getName());
    }

    @GetMapping("/franchises/{franchiseId}/top-products")
    public Flux<BranchTopProductResponse> getTopProducts(@PathVariable String franchiseId) {
        return getTopProductsUseCase.execute(franchiseId);
    }
}
