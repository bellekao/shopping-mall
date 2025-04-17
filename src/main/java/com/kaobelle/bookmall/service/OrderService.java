package com.kaobelle.bookmall.service;

import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
