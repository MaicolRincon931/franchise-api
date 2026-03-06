package com.accenture.franchise_api.infrastructure.repository;

import com.accenture.franchise_api.infrastructure.persistence.document.FranchiseDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface MongoFranchiseRepository extends ReactiveMongoRepository<FranchiseDocument, String> {
    Mono<FranchiseDocument> findByBranchesId(String branchId);

    Mono<FranchiseDocument> findByBranchesProductsId(String productId);
}
