package com.accenture.franchise_api.application.usecase;

import com.accenture.franchise_api.domain.exception.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddBranchUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private AddBranchUseCase addBranchUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldAddBranch_WhenFranchiseExists() {
        // Arrange
        String franchiseId = "f1";
        String branchName = "New Branch";
        Franchise franchise = Franchise.builder()
                .id(franchiseId)
                .name("Franchise 1")
                .branches(new ArrayList<>())
                .build();

        when(repository.findById(franchiseId)).thenReturn(Mono.just(franchise));
        when(repository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        // Act
        Mono<Franchise> result = addBranchUseCase.execute(franchiseId, branchName);

        // Assert
        StepVerifier.create(result)
                .assertNext(savedFranchise -> {
                    assertEquals(1, savedFranchise.getBranches().size());
                    assertEquals(branchName, savedFranchise.getBranches().get(0).getName());
                })
                .verifyComplete();

        verify(repository).findById(franchiseId);
        verify(repository).save(any(Franchise.class));
    }

    @Test
    void execute_ShouldThrowException_WhenFranchiseDoesNotExist() {
        // Arrange
        String franchiseId = "non-existent";
        String branchName = "New Branch";

        when(repository.findById(franchiseId)).thenReturn(Mono.empty());

        // Act
        Mono<Franchise> result = addBranchUseCase.execute(franchiseId, branchName);

        // Assert
        StepVerifier.create(result)
                .expectError(ResourceNotFoundException.class)
                .verify();

        verify(repository).findById(franchiseId);
        verify(repository, never()).save(any(Franchise.class));
    }
}
