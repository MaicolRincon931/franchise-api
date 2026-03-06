package com.accenture.franchise_api.infrastructure.persistence.adapter;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import com.accenture.franchise_api.infrastructure.persistence.mapper.FranchiseMapper;
import com.accenture.franchise_api.infrastructure.repository.MongoFranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoFranchiseRepositoryAdapter implements FranchiseRepository {

    private final MongoFranchiseRepository mongoRepository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return mongoRepository.save(FranchiseMapper.toDocument(franchise))
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return mongoRepository.findById(id)
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findByBranchId(String branchId) {
        return mongoRepository.findByBranchesId(branchId)
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findByProductId(String productId) {
        return mongoRepository.findByBranchesProductsId(productId)
                .map(FranchiseMapper::toDomain);
    }
}
