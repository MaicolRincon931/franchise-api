package com.accenture.franchise_api.infrastructure.repository;

import com.accenture.franchise_api.domain.model.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoFranchiseRepository extends ReactiveMongoRepository<Franchise, String> {
}
