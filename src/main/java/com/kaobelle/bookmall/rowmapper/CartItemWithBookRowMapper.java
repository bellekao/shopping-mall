package com.kaobelle.bookmall.rowmapper;

import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.model.CartItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemWithBookRowMapper implements RowMapper<CartItem> {

    @Override
    public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setUserId(rs.getInt("user_id"));
        cartItem.setBookId(rs.getInt("book_id"));
        cartItem.setQuantity(rs.getInt("quantity"));

        Book book = new Book();
        book.setImageUrl(rs.getString("image_url"));
        book.setPrice(rs.getInt("price"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));

        cartItem.setBook(book);
        return cartItem;
    }
}
