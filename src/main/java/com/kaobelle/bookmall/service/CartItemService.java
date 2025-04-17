package com.kaobelle.bookmall.service;

import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> getCart(Integer userId);

    CartItem getCartItemByUserIdAndBookId(Integer userId, Integer bookId);

    CreateOrderRequest getCartForOrder(Integer userId);

    CartItem createCartItem(Integer userId, Integer bookId);

    CartItem updateCartItem(Integer userId, Integer bookId, Integer quantity);

    void deleteCartItem(Integer userId, Integer bookId);

    void deleteCart(Integer userId);
}
