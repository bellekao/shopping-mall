package com.kaobelle.bookmall.controller;

import com.kaobelle.bookmall.dto.ApiResponse;
import com.kaobelle.bookmall.dto.CartItemUpdateRequest;
import com.kaobelle.bookmall.model.CartItem;
import com.kaobelle.bookmall.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/api/carts/{userId}")
    public ResponseEntity<ApiResponse<List<CartItem>>> getCart(@PathVariable Integer userId) {
        List<CartItem> cartItemList = cartItemService.getCart(userId);

        return ResponseEntity.ok(ApiResponse.success("讀取購物車列表成功", cartItemList));
    }

    @GetMapping("/api/carts/{userId}/{bookId}")
    public ResponseEntity<ApiResponse<CartItem>> getCartItem(@PathVariable Integer userId, @PathVariable Integer bookId) {
        CartItem cartItem = cartItemService.getCartItemByUserIdAndBookId(userId, bookId);

        return ResponseEntity.ok(ApiResponse.success("查詢特定書籍成功", cartItem));
    }

    @PostMapping("/api/carts/{userId}/{bookId}")
    public ResponseEntity<ApiResponse<CartItem>> createCartItem(@PathVariable Integer userId, @PathVariable Integer bookId) {
        CartItem cartItem = cartItemService.createCartItem(userId, bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("新增書籍到購物車" , cartItem));
    }

    @PutMapping("/api/carts/{userId}/{bookId}")
    public ResponseEntity<ApiResponse<CartItem>> updateCartItem(@PathVariable Integer userId,
                                                   @PathVariable Integer bookId,
                                                   @RequestBody @Valid CartItemUpdateRequest cartItemUpdateRequest) {
        CartItem cartItem = cartItemService.updateCartItem(userId, bookId, cartItemUpdateRequest.getQuantity());

        return ResponseEntity.ok(ApiResponse.success("更新數量成功", cartItem));
    }

    @DeleteMapping("/api/carts/{userId}/{bookId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Integer userId,
                                            @PathVariable Integer bookId) {
        cartItemService.deleteCartItem(userId, bookId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/api/carts/{userId}")
    public ResponseEntity<?> deleteCart(@PathVariable Integer userId) {
        cartItemService.deleteCart(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
