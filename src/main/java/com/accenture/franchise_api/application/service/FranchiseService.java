package com.accenture.franchise_api.application.service;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.infrastructure.repository.MongoFranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final MongoFranchiseRepository franchiseRepository;

    public Mono<Franchise> createFranchise(String name) {
        Franchise franchise = Franchise.builder()
                .name(name)
                .branches(new ArrayList<>())
                .build();
        return franchiseRepository.save(franchise);
    }
}
