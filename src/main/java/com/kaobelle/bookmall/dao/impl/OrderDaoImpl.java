package com.kaobelle.bookmall.dao.impl;

import com.kaobelle.bookmall.dao.OrderDao;
import com.kaobelle.bookmall.model.Order;
import com.kaobelle.bookmall.model.OrderItem;
import com.kaobelle.bookmall.rowmapper.OrderItemRowMapper;
import com.kaobelle.bookmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, created_date, total_amount, order_status" +
                " FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orderList.size() > 0) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.book_id, oi.quantity, oi.amount, b.title, b.image_url" +
                " FROM `order_item` AS oi LEFT JOIN `book` AS b ON oi.book_id = b.book_id" +
                " WHERE order_id = :orderId;";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order`(user_id, created_date, total_amount, order_status)" +
                " VALUES (:userId, :createdDate, :totalAmount, :orderStatus);";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("createdDate", new Date());
        map.put("totalAmount", totalAmount);
        map.put("orderStatus", 0);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO `order_item`(order_id, book_id, quantity, amount)" +
                " VALUES (:orderId, :bookId, :quantity, :amount);";

        for (OrderItem orderItem : orderItemList) {
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("bookId", orderItem.getBookId());
            map.put("quantity", orderItem.getQuantity());
            map.put("amount", orderItem.getAmount());

            KeyHolder keyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(
                    sql,
                    new MapSqlParameterSource(map),
                    keyHolder,
                    new String[]{"order_item_id"}
            );

            Integer orderItemId = keyHolder.getKey().intValue();
            System.out.println("新增的 order_item_id: " + orderItemId); // 想拿來做後續處理的話可以用
        }
    }
}
