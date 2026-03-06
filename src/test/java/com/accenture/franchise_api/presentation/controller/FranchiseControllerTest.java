package com.accenture.franchise_api.presentation.controller;

import com.accenture.franchise_api.application.usecase.CreateFranchiseUseCase;
import com.accenture.franchise_api.application.usecase.GetTopProductsUseCase;
import com.accenture.franchise_api.application.usecase.UpdateFranchiseNameUseCase;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.presentation.dto.CreateFranchiseRequest;
import com.accenture.franchise_api.presentation.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FranchiseController.class)
@Import(GlobalExceptionHandler.class)
class FranchiseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateFranchiseUseCase createFranchiseUseCase;

    @MockitoBean
    private UpdateFranchiseNameUseCase updateFranchiseNameUseCase;

    @MockitoBean
    private GetTopProductsUseCase getTopProductsUseCase;

    @Test
    void createFranchise_ShouldReturn200_WhenRequestIsValid() {
        CreateFranchiseRequest request = new CreateFranchiseRequest("Valid Name");
        Franchise franchise = Franchise.builder().id("1").name("Valid Name").branches(new ArrayList<>()).build();

        when(createFranchiseUseCase.execute(anyString())).thenReturn(Mono.just(franchise));

        webTestClient.post()
                .uri("/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Valid Name");
    }

    @Test
    void createFranchise_ShouldReturn400_WhenNameIsBlank() {
        CreateFranchiseRequest request = new CreateFranchiseRequest(""); // Blank name

        webTestClient.post()
                .uri("/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("name: El nombre de la franquicia no puede estar vacío");
    }
}
