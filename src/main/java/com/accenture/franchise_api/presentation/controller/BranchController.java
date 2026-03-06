package com.accenture.franchise_api.presentation.controller;

import com.accenture.franchise_api.application.usecase.AddBranchUseCase;
import com.accenture.franchise_api.application.usecase.UpdateBranchNameUseCase;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.presentation.dto.CreateBranchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BranchController {

    private final AddBranchUseCase addBranchUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;

    @PostMapping("/franchises/{franchiseId}/branches")
    public Mono<Franchise> addBranch(@PathVariable String franchiseId,
            @Valid @RequestBody CreateBranchRequest request) {
        return addBranchUseCase.execute(franchiseId, request.getName());
    }

    @PutMapping("/branches/{branchId}/name")
    public Mono<Franchise> updateBranchName(@PathVariable String branchId,
            @Valid @RequestBody CreateBranchRequest request) {
        return updateBranchNameUseCase.execute(branchId, request.getName());
    }
}
