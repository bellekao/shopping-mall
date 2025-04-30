package com.kaobelle.bookmall.controller;

import com.kaobelle.bookmall.dto.ApiResponse;
import com.kaobelle.bookmall.dto.CartItemUpdateRequest;
import com.kaobelle.bookmall.model.CartItem;
import com.kaobelle.bookmall.model.User;
import com.kaobelle.bookmall.service.CartItemService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/api/carts")
    public ResponseEntity<ApiResponse<List<CartItem>>> getCart(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "使用者尚未登入");
        }

        List<CartItem> cartItemList = cartItemService.getCart(user.getUserId());

        return ResponseEntity.ok(ApiResponse.success("讀取購物車列表成功", cartItemList));
    }

    @GetMapping("/api/carts/{userId}/{bookId}")
    public ResponseEntity<ApiResponse<CartItem>> getCartItem(@PathVariable Integer userId, @PathVariable Integer bookId) {
        CartItem cartItem = cartItemService.getCartItemByUserIdAndBookId(userId, bookId);

        return ResponseEntity.ok(ApiResponse.success("查詢特定書籍成功", cartItem));
    }

    @PostMapping("/api/carts/{bookId}")
    public ResponseEntity<ApiResponse<CartItem>> createCartItem(@PathVariable Integer bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("未登入"));
        }

        Integer userId = user.getUserId();
        CartItem cartItem = cartItemService.createCartItem(userId, bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("新增書籍到購物車", cartItem));
    }

    @PutMapping("/api/carts/{cartItemId}")
    public ResponseEntity<ApiResponse<CartItem>> updateCartItem(@PathVariable Integer cartItemId,
                                                                @RequestBody @Valid CartItemUpdateRequest cartItemUpdateRequest,
                                                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        CartItem cartItem = cartItemService.updateCartItem(cartItemId, user.getUserId(), cartItemUpdateRequest.getQuantity());
        return ResponseEntity.ok(ApiResponse.success("更新數量成功", cartItem));
    }

    @DeleteMapping("/api/carts/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Integer cartItemId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        CartItem cartItem = cartItemService.getCartItemByCartItemId(cartItemId);
        if (cartItem == null || !cartItem.getUserId().equals(user.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "購物車中無此書籍");
        }

        cartItemService.deleteCartItem(user.getUserId(), cartItem.getBookId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/carts")
    public ResponseEntity<?> clearCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "尚未登入");
        }

        try {
            cartItemService.clearCart(user.getUserId());
            return ResponseEntity.ok(Map.of("message", "購物車已清空"));
        } catch (Exception e) {
            // 捕獲並回傳錯誤訊息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "清空購物車失敗", "error", e.getMessage()));
        }
    }
}
