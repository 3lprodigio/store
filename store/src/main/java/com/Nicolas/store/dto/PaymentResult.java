package com.Nicolas.store.dto;

public record PaymentResult (boolean success, String transactionId, String errorMessage){
    
}
