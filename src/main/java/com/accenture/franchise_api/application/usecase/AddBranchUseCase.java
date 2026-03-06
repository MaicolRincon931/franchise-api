package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.exception.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddBranchUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String franchiseId, String branchName) {
        return repository.findById(franchiseId)
                .switchIfEmpty(
                        Mono.error(new ResourceNotFoundException("Franquicia no encontrada con ID: " + franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = Branch.builder()
                            .id(UUID.randomUUID().toString())
                            .name(branchName)
                            .products(new ArrayList<>())
                            .build();
                    franchise.getBranches().add(branch);
                    return repository.save(franchise);
                });
    }
}
