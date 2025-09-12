package com.Nicolas.store;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.Nicolas.store.dto.OrderRequest;
import com.Nicolas.store.dto.OrderResult;
import com.Nicolas.store.dto.PaymentResult;

@SpringBootTest
public class OrderServiceTest {
    private StripePaymentService paymentService;
    private OrderService orderService;

    @BeforeEach
    void setup(){
        paymentService = Mockito.mock(StripePaymentService.class);
        orderService = new OrderService(paymentService);
    }

    @Test
    void placeOrder_successfulPayment_returnsOrderPlaced(){
        when(paymentService.processPayment(anyDouble(), anyString()))
            .thenReturn(new PaymentResult(true,"tx_123",null));

        OrderRequest req = new OrderRequest(99.9,"p1");
        OrderResult res = orderService.placeOrder(req);

        assertTrue(res.success());
        assertNotNull(res.orderId());
        assertEquals("ORDER_PLACED", res.message());
    }

    @Test
    void placeOrder_paymentFailed_returnsPaymentFailed(){
        when(paymentService.processPayment(anyDouble(), anyString()))
            .thenReturn(new PaymentResult(false,null,"insufficient funds"));

        OrderRequest req = new OrderRequest(50.0,"p2");
        OrderResult res = orderService.placeOrder(req);

        assertFalse(res.success());
        assertNotNull(res.orderId());
        assertTrue(res.message().startsWith("PAYMENT_FAILED"));
    }
}
