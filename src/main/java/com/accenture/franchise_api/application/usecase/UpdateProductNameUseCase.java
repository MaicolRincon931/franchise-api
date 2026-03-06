package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateProductNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String productId, String newName) {
        return repository.findByProductId(productId)
                .flatMap(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        branch.getProducts().stream()
                                .filter(p -> p.getId().equals(productId))
                                .findFirst()
                                .ifPresent(p -> p.setName(newName));
                    });
                    return repository.save(franchise);
                });
    }
}
