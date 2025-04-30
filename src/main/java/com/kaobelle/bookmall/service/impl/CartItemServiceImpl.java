package com.kaobelle.bookmall.service.impl;

import com.kaobelle.bookmall.dao.BookDao;
import com.kaobelle.bookmall.dao.CartItemDao;
import com.kaobelle.bookmall.dto.BuyItem;
import com.kaobelle.bookmall.dto.CreateOrderRequest;
import com.kaobelle.bookmall.model.Book;
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

    @Autowired
    private BookDao bookDao;

    @Override
    public List<CartItem> getCart(Integer userId) {
        List<CartItem> cartItemList = cartItemDao.getCart(userId);

        return cartItemList;
    }

    @Override
    public CartItem getCartItemByCartItemId(Integer cartItemId) {
        return cartItemDao.getCartItemByCartItemId(cartItemId);
    }

    @Override
    public CartItem getCartItemByUserIdAndBookId(Integer userId, Integer bookId) {
        CartItem cartItem = cartItemDao.getCartItemByUserIdAndBookId(userId, bookId);

        if (cartItem == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "購物車中無此書籍");
        }

        return cartItem;
    }

//    @Override
//    public CreateOrderRequest getCartForOrder(Integer userId) {
//        // 取得購物車內的所有書籍清單
//        List<CartItem> cartItemList = cartItemDao.getCartForOrder(userId);
//
//        // 將購物車內的所有書籍清單 放入 buyItemList
//        List<BuyItem> buyItemList = new ArrayList<>();
//
//        for (CartItem cartItem : cartItemList) {
//            BuyItem buyItem = new BuyItem();
//            buyItem.setBookId(cartItem.getBookId());
//            buyItem.setQuantity(cartItem.getQuantity());
//            buyItemList.add(buyItem);
//        }
//
//        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
//        createOrderRequest.setBuyItemList(buyItemList);
//
//        return createOrderRequest;
//    }

    @Override
    public CartItem createCartItem(Integer userId, Integer bookId) {
        CartItem existingItem = cartItemDao.getCartItemByUserIdAndBookId(userId, bookId);

        // 取得該書籍的庫存
        Book book = bookDao.getBookById(bookId);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到該書籍");
        }

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + 1;

            // 加入庫存檢查
            if (newQuantity > book.getStock()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "超過可購買的庫存上限：" + book.getStock());
            }

            cartItemDao.updateCartItem(userId, bookId, newQuantity);
            existingItem.setQuantity(newQuantity);
            System.out.println("Updated item: " + existingItem);
            return existingItem;
        } else {
            // 新增前也要檢查庫存是否至少為 1
            if (book.getStock() < 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "目前無庫存，無法加入購物車");
            }

            Integer cartItemId = cartItemDao.createCartItem(userId, bookId);
            CartItem newItem = cartItemDao.getCartItemByCartItemId(cartItemId);
            System.out.println("Created new item: " + newItem);
            return newItem;
        }
    }

    @Override
    public CartItem updateCartItem(Integer cartItemId, Integer userId, Integer quantity) {
        CartItem cartItem = cartItemDao.getCartItemByCartItemId(cartItemId);

        // 驗證 cartItem 是否存在且屬於此 user
        if (cartItem == null || !cartItem.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "購物車中無此書籍");
        }

        // 取得該書籍的庫存
        Book book = bookDao.getBookById(cartItem.getBookId());
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到該書籍");
        }

        // 比對庫存
        if (quantity > book.getStock()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "超過可購買的庫存上限：" + book.getStock());
        }

        // 更新數量（使用 userId + bookId）
        cartItemDao.updateCartItem(userId, cartItem.getBookId(), quantity);

        // 回傳更新後的 cartItem
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    @Override
    public void deleteCartItem(Integer userId, Integer bookId) {
        cartItemDao.deleteCartItem(userId, bookId);
    }

    @Override
    public void clearCart(Integer userId) {
        cartItemDao.deleteCart(userId);
    }
}
