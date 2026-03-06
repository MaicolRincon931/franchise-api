package com.accenture.franchise_api.infrastructure.persistence.mapper;

import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.infrastructure.persistence.document.BranchDocument;
import com.accenture.franchise_api.infrastructure.persistence.document.FranchiseDocument;
import com.accenture.franchise_api.infrastructure.persistence.document.ProductDocument;

import java.util.stream.Collectors;

public class FranchiseMapper {

    public static Franchise toDomain(FranchiseDocument document) {
        if (document == null)
            return null;
        return Franchise.builder()
                .id(document.getId())
                .name(document.getName())
                .branches(document.getBranches() != null
                        ? document.getBranches().stream().map(FranchiseMapper::toDomain).collect(Collectors.toList())
                        : null)
                .build();
    }

    public static FranchiseDocument toDocument(Franchise domain) {
        if (domain == null)
            return null;
        return FranchiseDocument.builder()
                .id(domain.getId())
                .name(domain.getName())
                .branches(domain.getBranches() != null
                        ? domain.getBranches().stream().map(FranchiseMapper::toDocument).collect(Collectors.toList())
                        : null)
                .build();
    }

    private static Branch toDomain(BranchDocument document) {
        return Branch.builder()
                .id(document.getId())
                .name(document.getName())
                .products(document.getProducts() != null
                        ? document.getProducts().stream().map(FranchiseMapper::toDomain).collect(Collectors.toList())
                        : null)
                .build();
    }

    private static BranchDocument toDocument(Branch domain) {
        return BranchDocument.builder()
                .id(domain.getId())
                .name(domain.getName())
                .products(domain.getProducts() != null
                        ? domain.getProducts().stream().map(FranchiseMapper::toDocument).collect(Collectors.toList())
                        : null)
                .build();
    }

    private static Product toDomain(ProductDocument document) {
        return Product.builder()
                .id(document.getId())
                .name(document.getName())
                .stock(document.getStock())
                .build();
    }

    private static ProductDocument toDocument(Product domain) {
        return ProductDocument.builder()
                .id(domain.getId())
                .name(domain.getName())
                .stock(domain.getStock())
                .build();
    }
}
