package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.dto.BranchTopProductResponse;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class GetTopProductsUseCase {

    private final FranchiseRepository repository;

    public Flux<BranchTopProductResponse> execute(String franchiseId) {
        return repository.findById(franchiseId)
                .flatMapIterable(franchise -> {
                    if (franchise.getBranches() == null)
                        return new ArrayList<>();
                    return franchise.getBranches();
                })
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
