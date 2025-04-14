package com.kaobelle.bookmall.dao;

import com.kaobelle.bookmall.dto.BookRequest;
import com.kaobelle.bookmall.model.Book;

public interface BookDao {

    Book getBookById(Integer bookId);

    Integer createBook(BookRequest bookRequest);

    void updateBook(Integer bookId, BookRequest bookRequest);

    void deleteBookById(Integer bookId);
}
