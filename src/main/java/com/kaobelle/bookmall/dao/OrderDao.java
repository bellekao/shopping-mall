package com.kaobelle.bookmall.dao;

import com.kaobelle.bookmall.model.Order;
import com.kaobelle.bookmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    List<Order> getOrders(Integer userId);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItem(Integer orderId,List<OrderItem> orderItemList);
}
