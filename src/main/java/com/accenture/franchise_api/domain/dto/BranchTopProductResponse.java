package com.accenture.franchise_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchTopProductResponse {
    private String branchName;
    private String productName;
    private Integer stock;
}
