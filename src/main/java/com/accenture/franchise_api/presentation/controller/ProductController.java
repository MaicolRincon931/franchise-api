package com.accenture.franchise_api.presentation.controller;

import com.accenture.franchise_api.application.usecase.AddProductUseCase;
import com.accenture.franchise_api.application.usecase.DeleteProductUseCase;
import com.accenture.franchise_api.application.usecase.UpdateProductNameUseCase;
import com.accenture.franchise_api.application.usecase.UpdateProductStockUseCase;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.presentation.dto.CreateBranchRequest;
import com.accenture.franchise_api.presentation.dto.CreateProductRequest;
import com.accenture.franchise_api.presentation.dto.UpdateStockRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product", description = "Endpoints for managing products")
public class ProductController {

    private final AddProductUseCase addProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;

    @PostMapping("/branches/{branchId}/products")
    @Operation(summary = "Add a new product to a branch")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product added successfully"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    public Mono<Franchise> addProduct(@PathVariable String branchId, @Valid @RequestBody CreateProductRequest request) {
        return addProductUseCase.execute(branchId, request.getName(), request.getStock());
    }

    @DeleteMapping("/branches/{branchId}/products/{productId}")
    @Operation(summary = "Delete a product from a branch")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product or Branch not found")
    })
    public Mono<Map<String, String>> deleteProduct(@PathVariable String branchId, @PathVariable String productId) {
        return deleteProductUseCase.execute(branchId, productId)
                .then(Mono.just(Map.of("message", "Producto eliminado correctamente")));
    }

    @PutMapping("/products/{productId}/stock")
    @Operation(summary = "Update product stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product stock updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Mono<Franchise> updateProductStock(@PathVariable String productId,
            @Valid @RequestBody UpdateStockRequest request) {
        return updateProductStockUseCase.execute(productId, request.getStock());
    }

    @PutMapping("/products/{productId}/name")
    @Operation(summary = "Update product name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product name updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Mono<Franchise> updateProductName(@PathVariable String productId,
            @Valid @RequestBody CreateBranchRequest request) {
        return updateProductNameUseCase.execute(productId, request.getName());
    }
}
