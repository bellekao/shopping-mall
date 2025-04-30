package com.kaobelle.bookmall.service;

import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> getCart(Integer userId);

    CartItem getCartItemByCartItemId(Integer cartItemId);

    CartItem getCartItemByUserIdAndBookId(Integer userId, Integer bookId);

    // CreateOrderRequest getCartForOrder(Integer userId);

    CartItem createCartItem(Integer userId, Integer bookId);

    CartItem updateCartItem(Integer cartItemId, Integer userId, Integer quantity);

    void deleteCartItem(Integer userId, Integer bookId);

    void clearCart(Integer userId);
}
