package com.kaobelle.bookmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.Order;
import com.kaobelle.bookmall.service.CartItemService;
import com.kaobelle.bookmall.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/api/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                             @RequestBody @Valid CreateOrderRequest createOrderRequest) throws JsonProcessingException {

        // CreateOrderRequest createOrderRequest = cartItemService.getCartForOrder(userId);

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        ObjectMapper objectMapper = new ObjectMapper();
        log.info("createOrderRequest: {}", objectMapper.writeValueAsString(createOrderRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
