package com.kaobelle.bookmall.dao;

import com.kaobelle.bookmall.dto.BookQueryParams;
import com.kaobelle.bookmall.dto.BookRequest;
import com.kaobelle.bookmall.model.Book;

import java.util.List;

public interface BookDao {

    List<Book> getBooks(BookQueryParams bookQueryParams);

    Book getBookById(Integer bookId);

    Integer createBook(BookRequest bookRequest);

    void updateBook(Integer bookId, BookRequest bookRequest);

    void deleteBookById(Integer bookId);

    void updateStock(Integer bookId, Integer stock);
}
