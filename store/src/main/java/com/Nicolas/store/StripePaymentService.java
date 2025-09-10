package com.Nicolas.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Nicolas.store.dto.*;

@Service
public class StripePaymentService {

    private static final Logger logger = LoggerFactory.getLogger(StripePaymentService.class);

    public PaymentResult processPayment(double amount, String orderID){

        logger.info("Proccesing payment for orderID={} amount={}",orderID, amount);

        String txID = "tx_" + System.currentTimeMillis();
        return new PaymentResult(true,txID,null);
    }
}
