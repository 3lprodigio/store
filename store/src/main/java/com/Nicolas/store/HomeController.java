package com.Nicolas.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "index";
    }

    @PostMapping("/order")
    @ResponseBody
    public String placeOrder(){
        orderService.placeOrder();
        paymentService.processPayment(123.45);
        return "order placed";
    }
}
