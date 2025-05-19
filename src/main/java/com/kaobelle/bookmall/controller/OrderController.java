package com.kaobelle.bookmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaobelle.bookmall.dto.ApiResponse;
import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.Order;
import com.kaobelle.bookmall.model.User;
import com.kaobelle.bookmall.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/orders")
    public ResponseEntity<ApiResponse<List<Order>>> getOrders(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        List<Order> orderList = orderService.getOrders(user.getUserId());
        return ResponseEntity.ok(ApiResponse.success("成功取得訂單列表", orderList));
    }

    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<ApiResponse<Order>> getOrderDetail(@PathVariable Integer orderId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        // 獲取訂單詳情
        Order order = orderService.getOrderDetail(user.getUserId(), orderId);
        return ResponseEntity.ok(ApiResponse.success("成功取得訂單詳細資料", order));
    }

    @PostMapping("/api/users/{userId}/orders")
    public ResponseEntity<ApiResponse<Order>> createOrder(@PathVariable Integer userId,
                                             @RequestBody @Valid CreateOrderRequest createOrderRequest) throws JsonProcessingException {

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderDetail(userId, orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("訂單新增成功", order));
    }
}
