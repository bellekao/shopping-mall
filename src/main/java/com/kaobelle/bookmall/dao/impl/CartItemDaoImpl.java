package com.kaobelle.bookmall.dao.impl;

import com.kaobelle.bookmall.dao.CartItemDao;
import com.kaobelle.bookmall.model.CartItem;
import com.kaobelle.bookmall.rowmapper.CartItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartItemDaoImpl implements CartItemDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<CartItem> getCart(Integer userId) {
        String sql = "SELECT cart_item_id, user_id, book_id, quantity" +
                " FROM `cart_item` WHERE user_id = :userId";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<CartItem> cartItemList = namedParameterJdbcTemplate.query(sql, map, new CartItemRowMapper());

        return cartItemList;
    }

    @Override
    public CartItem getCartItemByCartItemId(Integer cartItemId) {
        String sql = "SELECT cart_item_id, user_id, book_id, quantity" +
                " FROM `cart_item` WHERE cart_item_id = :cartItemId";

        Map<String, Object> map = new HashMap<>();
        map.put("cartItemId", cartItemId);

        List<CartItem> cartItemList = namedParameterJdbcTemplate.query(sql, map, new CartItemRowMapper());

        if (cartItemList.size() > 0) {
            return cartItemList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public CartItem getCartItemByUserIdAndBookId(Integer userId, Integer bookId) {
        String sql = "SELECT cart_item_id, user_id, book_id, quantity" +
                " FROM `cart_item` WHERE user_id = :userId AND book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("bookId", bookId);

        List<CartItem> cartItemList = namedParameterJdbcTemplate.query(sql, map, new CartItemRowMapper());

        if (cartItemList.size() > 0) {
            return cartItemList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer getCartItemIdByUserIdAndBookId(Integer userId, Integer bookId) {
        String sql = "SELECT cart_item_id FROM `cart_item` WHERE user_id = :userId AND book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("bookId", bookId);

        List<CartItem> cartItemList = namedParameterJdbcTemplate.query(sql, map, new CartItemRowMapper());

        if (cartItemList.size() > 0) {
            return cartItemList.get(0).getCartItemId();
        } else {
            return null;
        }
    }

    @Override
    public Integer createCartItem(Integer userId, Integer bookId) {
        String sql = "INSERT INTO `cart_item`(user_id, book_id, quantity)" +
                " VALUES (:userId, :bookId, :quantity);";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("bookId", bookId);
        map.put("quantity", 1);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer cartItemId = keyHolder.getKey().intValue();

        return cartItemId;
    }

    @Override
    public void updateCartItem(Integer userId, Integer bookId, Integer quantity) {
        String sql = "UPDATE `cart_item` SET quantity = :quantity" +
                " WHERE user_id = :userId AND book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("bookId", bookId);
        map.put("quantity", quantity);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteCartItem(Integer userId, Integer bookId) {
        String sql = "DELETE FROM `cart_item` WHERE user_id = :userId AND book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("bookId", bookId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteCart(Integer userId) {
        String sql = "DELETE FROM `cart_item` WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
