package com.Nicolas.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Nicolas.store.dto.OrderRequest;
import com.Nicolas.store.dto.OrderResult;

@Controller
public class HomeController {
    private final OrderService orderService;
    private final StripePaymentService paymentService;

    public HomeController(OrderService orderService, StripePaymentService paymentService){
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @PostMapping("/order")
    @ResponseBody
    public OrderResult placeOrder(@RequestBody OrderRequest req){
        return orderService.placeOrder(req);
    }
}
