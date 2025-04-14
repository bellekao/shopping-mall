package com.kaobelle.bookmall.service.impl;

import com.kaobelle.bookmall.dao.BookDao;
import com.kaobelle.bookmall.dto.BookRequest;
import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookDao bookDao;

    @Override
    public Book getBookById(Integer bookId) {
        return bookDao.getBookById(bookId);
    }

    @Override
    public Integer createBook(BookRequest bookRequest) {
        return bookDao.createBook(bookRequest);
    }

    @Override
    public void updateBook(Integer bookId, BookRequest bookRequest) {
        Book book = bookDao.getBookById(bookId);

        // bookId 不存在
        if (book == null) {
            log.warn("該本 ID: {} 的書籍不存在", bookId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "該書籍不存在");
        }

        bookDao.updateBook(bookId, bookRequest);
    }

    @Override
    public void deleteBook(Integer bookId) {
        bookDao.deleteBookById(bookId);
    }
}
