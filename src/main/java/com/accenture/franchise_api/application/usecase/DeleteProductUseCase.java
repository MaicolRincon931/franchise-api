package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DeleteProductUseCase {

    private final FranchiseRepository repository;

    public Mono<Void> execute(String branchId, String productId) {
        return repository.findByBranchId(branchId)
                .flatMap(franchise -> {
                    franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> {
                                branch.getProducts().removeIf(p -> p.getId().equals(productId));
                            });
                    return repository.save(franchise);
                })
                .then();
    }
}
