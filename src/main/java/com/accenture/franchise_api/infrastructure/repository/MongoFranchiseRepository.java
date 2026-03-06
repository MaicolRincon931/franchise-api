package com.accenture.franchise_api.infrastructure.repository;

import com.accenture.franchise_api.domain.model.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface MongoFranchiseRepository extends ReactiveMongoRepository<Franchise, String> {
    Mono<Franchise> findByBranchesId(String branchId);
}
