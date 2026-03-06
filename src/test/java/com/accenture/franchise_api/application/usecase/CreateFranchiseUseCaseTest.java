package com.accenture.franchise_api.application.usecase;

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

class CreateFranchiseUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private CreateFranchiseUseCase createFranchiseUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldCreateFranchise() {
        // Arrange
        String name = "Test Franchise";
        Franchise expectedFranchise = Franchise.builder().name(name).branches(new ArrayList<>()).build();

        when(repository.save(any(Franchise.class))).thenReturn(Mono.just(expectedFranchise));

        // Act
        Mono<Franchise> result = createFranchiseUseCase.execute(name);

        // Assert
        StepVerifier.create(result)
                .assertNext(franchise -> {
                    assertEquals(name, franchise.getName());
                    assertEquals(0, franchise.getBranches().size());
                })
                .verifyComplete();

        verify(repository).save(any(Franchise.class));
    }
}
