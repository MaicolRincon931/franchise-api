package com.accenture.franchise_api.application.service;

import com.accenture.franchise_api.domain.dto.BranchTopProductResponse;
import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.infrastructure.repository.MongoFranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final MongoFranchiseRepository franchiseRepository;

    public Mono<Franchise> createFranchise(String name) {
        Franchise franchise = Franchise.builder()
                .name(name)
                .branches(new ArrayList<>())
                .build();
        return franchiseRepository.save(franchise);
    }

    public Mono<Franchise> addBranch(String franchiseId, String branchName) {
        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> {
                    Branch branch = Branch.builder()
                            .id(UUID.randomUUID().toString())
                            .name(branchName)
                            .products(new ArrayList<>())
                            .build();
                    franchise.getBranches().add(branch);
                    return franchiseRepository.save(franchise);
                });
    }

    public Mono<Franchise> addProduct(String branchId, String productName, Integer stock) {
        return franchiseRepository.findByBranchesId(branchId)
                .flatMap(franchise -> {
                    franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> {
                                Product product = Product.builder()
                                        .id(UUID.randomUUID().toString())
                                        .name(productName)
                                        .stock(stock)
                                        .build();
                                branch.getProducts().add(product);
                            });
                    return franchiseRepository.save(franchise);
                });
    }

    public Mono<Void> deleteProduct(String branchId, String productId) {
        return franchiseRepository.findByBranchesId(branchId)
                .flatMap(franchise -> {
                    franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> {
                                branch.getProducts().removeIf(p -> p.getId().equals(productId));
                            });
                    return franchiseRepository.save(franchise);
                })
                .then();
    }

    public Flux<BranchTopProductResponse> getTopProducts(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .flatMapIterable(Franchise::getBranches)
                .map(branch -> {
                    Product topProduct = branch.getProducts().stream()
                            .max(Comparator.comparingInt(Product::getStock))
                            .orElse(Product.builder().name("No Products").stock(0).build());

                    return BranchTopProductResponse.builder()
                            .branchName(branch.getName())
                            .productName(topProduct.getName())
                            .stock(topProduct.getStock())
                            .build();
                });
    }
}
