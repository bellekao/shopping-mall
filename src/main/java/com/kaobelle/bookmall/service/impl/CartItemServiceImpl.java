package com.kaobelle.bookmall.service.impl;

import com.kaobelle.bookmall.dao.CartItemDao;
import com.kaobelle.bookmall.dto.BuyItem;
import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.CartItem;
import com.kaobelle.bookmall.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemDao cartItemDao;

    @Override
    public List<CartItem> getCart(Integer userId) {
        List<CartItem> cartItemList = cartItemDao.getCart(userId);

        return cartItemList;
    }

    @Override
    public CartItem getCartItemByUserIdAndBookId(Integer userId, Integer bookId) {
        CartItem cartItem = cartItemDao.getCartItemByUserIdAndBookId(userId, bookId);

        if (cartItem == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "購物車中無此書籍");
        }

        return cartItem;
    }

    @Override
    public CreateOrderRequest getCartForOrder(Integer userId) {
        // 取得購物車內的所有書籍清單
        List<CartItem> cartItemList = cartItemDao.getCartForOrder(userId);

        // 將購物車內的所有書籍清單 放入 buyItemList
        List<BuyItem> buyItemList = new ArrayList<>();

        for (CartItem cartItem : cartItemList) {
            BuyItem buyItem = new BuyItem();
            buyItem.setBookId(cartItem.getBookId());
            buyItem.setQuantity(cartItem.getQuantity());
            buyItemList.add(buyItem);
        }

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        return createOrderRequest;
    }

    @Override
    public CartItem createCartItem(Integer userId, Integer bookId) {
        Integer cartItemId = cartItemDao.createCartItem(userId, bookId);

        CartItem cartItem = cartItemDao.getCartItemByCartItemId(cartItemId);

        return cartItem;
    }

    @Override
    public CartItem updateCartItem(Integer userId, Integer bookId, Integer quantity) {
        // 該筆 CartItem 是否存在
        CartItem cartItem = cartItemDao.getCartItemByUserIdAndBookId(userId, bookId);

        if (cartItem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "購物車中無此書籍");
        }

        // 執行更新
        cartItemDao.updateCartItem(userId, bookId, quantity);

        CartItem updateCartItem = cartItemDao.getCartItemByUserIdAndBookId(userId, bookId);

        return updateCartItem;
    }

    @Override
    public void deleteCartItem(Integer userId, Integer bookId) {
        cartItemDao.deleteCartItem(userId, bookId);
    }

    @Override
    public void deleteCart(Integer userId) {
        cartItemDao.deleteCart(userId);
    }
}
