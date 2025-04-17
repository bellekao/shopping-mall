package com.kaobelle.bookmall.dto;

import jakarta.validation.constraints.NotNull;

public class BuyItem {
    @NotNull
    private Integer bookId;

    @NotNull
    private Integer quantity;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
