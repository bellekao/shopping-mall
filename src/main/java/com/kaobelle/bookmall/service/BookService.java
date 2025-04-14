package com.kaobelle.bookmall.service;

import com.kaobelle.bookmall.dto.BookQueryParams;
import com.kaobelle.bookmall.dto.BookRequest;
import com.kaobelle.bookmall.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getBooks(BookQueryParams bookQueryParams);

    Book getBookById(Integer bookId);

    Integer createBook(BookRequest bookRequest);

    void updateBook(Integer bookId, BookRequest bookRequest);

    void deleteBook(Integer bookId);
}
