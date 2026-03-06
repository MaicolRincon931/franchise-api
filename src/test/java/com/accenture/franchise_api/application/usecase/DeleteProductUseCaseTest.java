package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.exception.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteProductUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldDeleteProduct_WhenProductExists() {
        // Arrange
        String branchId = "b1";
        String productId = "p1";
        Product product = Product.builder().id(productId).name("Product 1").stock(10).build();
        Branch branch = Branch.builder().id(branchId).name("Branch 1").products(new ArrayList<>(List.of(product)))
                .build();
        Franchise franchise = Franchise.builder().id("f1").branches(new ArrayList<>(List.of(branch))).build();

        when(repository.findByBranchId(branchId)).thenReturn(Mono.just(franchise));
        when(repository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        // Act
        Mono<Void> result = deleteProductUseCase.execute(branchId, productId);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findByBranchId(branchId);
        verify(repository).save(any(Franchise.class));
    }

    @Test
    void execute_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        String branchId = "b1";
        String productId = "non-existent";
        Branch branch = Branch.builder().id(branchId).name("Branch 1").products(new ArrayList<>()).build();
        Franchise franchise = Franchise.builder().id("f1").branches(new ArrayList<>(List.of(branch))).build();

        when(repository.findByBranchId(branchId)).thenReturn(Mono.just(franchise));

        // Act
        Mono<Void> result = deleteProductUseCase.execute(branchId, productId);

        // Assert
        StepVerifier.create(result)
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(repository).findByBranchId(branchId);
        verify(repository, never()).save(any(Franchise.class));
    }
}
