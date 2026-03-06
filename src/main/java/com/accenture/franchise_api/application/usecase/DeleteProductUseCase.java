package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.exception.ResourceNotFoundException;
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
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sucursal no encontrada con ID: " + branchId)))
                .flatMap(franchise -> {
                    franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> {
                                boolean removed = branch.getProducts().removeIf(p -> p.getId().equals(productId));
                                if (!removed) {
                                    throw new ResourceNotFoundException("Producto no encontrado con ID: " + productId);
                                }
                            });
                    return repository.save(franchise);
                })
                .then();
    }
}
