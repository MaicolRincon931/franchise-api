package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.exception.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateFranchiseNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String id, String newName) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franquicia no encontrada con ID: " + id)))
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return repository.save(franchise);
                });
    }
}
