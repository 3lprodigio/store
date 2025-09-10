package com.Nicolas.store.dto;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
    @Positive(message = "amount must be positive") double amount,
    @NotBlank(message = "productId is required") String productId
) { }
