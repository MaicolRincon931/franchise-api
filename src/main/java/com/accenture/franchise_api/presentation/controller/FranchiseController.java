package com.accenture.franchise_api.presentation.controller;

import com.accenture.franchise_api.application.service.FranchiseService;
import com.accenture.franchise_api.domain.model.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @PostMapping("/franchises")
    public Mono<Franchise> createFranchise(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        return franchiseService.createFranchise(name);
    }

    @PostMapping("/franchises/{franchiseId}/branches")
    public Mono<Franchise> addBranch(@PathVariable String franchiseId, @RequestBody Map<String, String> request) {
        String name = request.get("name");
        return franchiseService.addBranch(franchiseId, name);
    }

    @PostMapping("/branches/{branchId}/products")
    public Mono<Franchise> addProduct(@PathVariable String branchId, @RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        Integer stock = (Integer) request.get("stock");
        return franchiseService.addProduct(branchId, name, stock);
    }
}
