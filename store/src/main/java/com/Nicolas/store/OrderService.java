package com.Nicolas.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Nicolas.store.dto.*;

@Service
public class OrderService {//Manage orders (create,validate, save order state, B rules, coordinate payment)
    private final StripePaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    // Constructor corregido (parámetro en minúscula)
    public OrderService(StripePaymentService paymentService){
        this.paymentService = paymentService;
    }

    public OrderResult placeOrder(OrderRequest req){
        //validate request
        if(req.amount() <= 0) return new OrderResult(false,null,"invalid amount");
    
        //create/save provisional order
        String orderId = "order_" + System.currentTimeMillis();
        PaymentResult pr = paymentService.processPayment(req.amount(), orderId);
        if(pr.success()){
                //mark order as paid
            logger.info("order {} paid, tx={}",orderId,pr.transactionId());
            return new OrderResult(true, orderId, "ORDER_PLACED");
        }else{
            //mark order as failed /try rollback
            logger.warn("Payment failed for order {}: {}",orderId,pr.errorMessage());
            return new OrderResult(false, orderId, "PAYMENT_FAILED"+pr.errorMessage());
        }

    } 
}

