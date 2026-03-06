package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.exception.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddProductUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String branchId, String productName, Integer stock) {
        return repository.findByBranchId(branchId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sucursal no encontrada con ID: " + branchId)))
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
                    return repository.save(franchise);
                });
    }
}
