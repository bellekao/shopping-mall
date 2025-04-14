package com.kaobelle.bookmall.rowmapper;

import com.kaobelle.bookmall.constant.BookCategory;
import com.kaobelle.bookmall.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setCategory(BookCategory.valueOf(rs.getString("category")));
        book.setImageUrl(rs.getString("image_url"));
        book.setPrice(rs.getInt("price"));
        book.setStock(rs.getInt("stock"));
        book.setSale(rs.getInt("sale"));

        return book;
    }
}
