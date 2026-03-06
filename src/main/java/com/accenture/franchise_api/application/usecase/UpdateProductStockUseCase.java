package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.exception.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateProductStockUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String productId, Integer newStock) {
        return repository.findByProductId(productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado con ID: " + productId)))
                .flatMap(franchise -> {
                    franchise.getBranches().forEach(branch -> {
                        branch.getProducts().stream()
                                .filter(p -> p.getId().equals(productId))
                                .findFirst()
                                .ifPresent(p -> p.setStock(newStock));
                    });
                    return repository.save(franchise);
                });
    }
}
