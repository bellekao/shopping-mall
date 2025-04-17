package com.kaobelle.bookmall.dao;

import com.kaobelle.bookmall.model.CartItem;

import java.util.List;

public interface CartItemDao{

    List<CartItem> getCart(Integer userId);

    CartItem getCartItemByCartItemId(Integer cartItemId);

    CartItem getCartItemByUserIdAndBookId(Integer userId, Integer bookId);

    Integer getCartItemIdByUserIdAndBookId(Integer userId, Integer bookId);

    Integer createCartItem(Integer userId, Integer bookId);

    void updateCartItem(Integer userId, Integer bookId, Integer quantity);

    void deleteCartItem(Integer userId, Integer bookId);

    void deleteCart(Integer userId);

    List<CartItem> getCartForOrder(Integer userId);
}
