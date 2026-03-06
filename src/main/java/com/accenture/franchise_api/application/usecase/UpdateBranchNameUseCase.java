package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateBranchNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String branchId, String newName) {
        return repository.findByBranchId(branchId)
                .flatMap(franchise -> {
                    franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> branch.setName(newName));
                    return repository.save(franchise);
                });
    }
}
