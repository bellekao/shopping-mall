package com.kaobelle.bookmall.rowmapper;

import com.kaobelle.bookmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setCreatedDate(rs.getTimestamp("created_date"));
        order.setTotalAmount(rs.getInt("total_amount"));
        order.setOrderStatus(rs.getInt("order_status"));
        return order;
    }
}
