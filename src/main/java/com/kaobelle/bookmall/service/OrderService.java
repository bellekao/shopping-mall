package com.kaobelle.bookmall.service;

import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders(Integer userId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
