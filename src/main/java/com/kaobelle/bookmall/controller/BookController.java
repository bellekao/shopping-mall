package com.kaobelle.bookmall.controller;

import com.kaobelle.bookmall.dto.BookRequest;
import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/api/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Integer bookId) {
        Book book = bookService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

//    @GetMapping("/api/books")
//    public ResponseEntity<List<Book>> getBooks() {
//        List<Book> bookList = bookService.getBooks();
//
//        return ResponseEntity.status(HttpStatus.OK).body(bookList);
//    }

    @PostMapping("/api/books")
    public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest) {
        Integer bookId = bookService.createBook(bookRequest);

        Book book = bookService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/api/books/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody BookRequest bookRequest,
                                           @PathVariable Integer bookId) {
        bookService.updateBook(bookId, bookRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // delete
    @DeleteMapping("/api/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
