package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CreateFranchiseUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String name) {
        Franchise franchise = Franchise.builder()
                .name(name)
                .branches(new ArrayList<>())
                .build();
        return repository.save(franchise);
    }
}
