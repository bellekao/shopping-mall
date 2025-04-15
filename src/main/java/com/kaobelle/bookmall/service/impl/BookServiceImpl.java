package com.kaobelle.bookmall.service.impl;

import com.kaobelle.bookmall.dao.BookDao;
import com.kaobelle.bookmall.dto.BookQueryParams;
import com.kaobelle.bookmall.dto.BookRequest;
import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getBooks(BookQueryParams bookQueryParams) {
        return bookDao.getBooks(bookQueryParams);
    }

    @Override
    public Book getBookById(Integer bookId) {
        Book book = bookDao.getBookById(bookId);

        if (book == null) {
            log.warn("找不到此書籍 (bookId: {})", bookId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到此書籍");
        }

        return book;
    }

    @Override
    public Integer createBook(BookRequest bookRequest) {
        return bookDao.createBook(bookRequest);
    }

    @Override
    public void updateBook(Integer bookId, BookRequest bookRequest) {
        Book book = bookDao.getBookById(bookId);

        // 檢查 商品(bookId) 是否存在
        if (book == null) {
            log.warn("找不到此書籍 (bookId: {})", bookId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "該書籍不存在");
        }

        // 修改 商品(bookId) 資訊
        bookDao.updateBook(bookId, bookRequest);
    }

    @Override
    public void deleteBook(Integer bookId) {
        bookDao.deleteBookById(bookId);
    }
}
