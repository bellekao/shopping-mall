package com.kaobelle.bookmall.service.impl;

import com.kaobelle.bookmall.dao.BookDao;
import com.kaobelle.bookmall.dao.CartItemDao;
import com.kaobelle.bookmall.dao.OrderDao;
import com.kaobelle.bookmall.dao.UserDao;
import com.kaobelle.bookmall.dto.BuyItem;
import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.model.Order;
import com.kaobelle.bookmall.model.OrderItem;
import com.kaobelle.bookmall.model.User;
import com.kaobelle.bookmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private CartItemDao cartItemDao;

    @Override
    public List<Order> getOrders(Integer userId) {
        List<Order> orderList = orderDao.getOrders(userId);

        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 檢查 userId 是否存在
        User user = userDao.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "此帳號不存在");
        }

        // 創建訂單
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Book book = bookDao.getBookById(buyItem.getBookId());

            // 檢查書籍是否存在、庫存是否足夠
            if (book == null) {
                log.warn("書籍 {} 不存在", buyItem.getBookId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該書籍不存在");
            } else if (book.getStock() < buyItem.getQuantity()) {
                log.warn("書籍 {} 不足。欲購買數量:{}，現有庫存量:{}", buyItem.getBookId(), buyItem.getQuantity(), book.getStock());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該書籍庫存不足");
            }

            // 扣除商品庫存
            bookDao.updateStock(buyItem.getBookId(), book.getStock() - buyItem.getQuantity());

            // 計算購買金額
            int amount = book.getPrice() * buyItem.getQuantity();
            totalAmount = totalAmount + amount;

            OrderItem orderItem = new OrderItem();
            orderItem.setBookId(buyItem.getBookId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);

            // 於購物車中清除該商品
            cartItemDao.deleteCartItem(userId, buyItem.getBookId());
        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItem(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderDetail(Integer userId, Integer orderId) {
        // 根據 orderId 查詢訂單，並取得該訂單的 userId
        Order order = orderDao.getOrderById(orderId);

        // 檢查該訂單是否存在，並且是否屬於當前登入的用戶
        if (order == null || !order.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "無權限查看此訂單");
        }

        // 查詢訂單項目
        List<OrderItem> orderItems = orderDao.getOrderItemByOrderId(orderId);
        if (orderItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到訂單項目");
        }

        order.setOrderItemList(orderItems);  // 設定訂單中的項目清單
        return order;  // 返回完整的訂單資訊（包括訂單項目）
    }
}
