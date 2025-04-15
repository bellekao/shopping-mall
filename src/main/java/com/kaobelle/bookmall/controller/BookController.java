package com.kaobelle.bookmall.controller;

import com.kaobelle.bookmall.constant.BookCategory;
import com.kaobelle.bookmall.dto.ApiResponse;
import com.kaobelle.bookmall.dto.BookQueryParams;
import com.kaobelle.bookmall.dto.BookRequest;
import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/api/books")
    public ResponseEntity<ApiResponse<List<Book>>> getBooks(
            // Filtering
            @RequestParam(required = false) BookCategory category,
            @RequestParam(required = false) String search,
            // Sorting
            @RequestParam(defaultValue = "price") String orderBy,
            @RequestParam(defaultValue = "desc") String sort
    ) {

        BookQueryParams bookQueryParams = new BookQueryParams();
        bookQueryParams.setCategory(category);
        bookQueryParams.setSearch(search);
        bookQueryParams.setOrderBy(orderBy);
        bookQueryParams.setSort(sort);

        List<Book> bookList = bookService.getBooks(bookQueryParams);

        return ResponseEntity.ok(ApiResponse.success("成功取得書籍列表", bookList));
    }

    @GetMapping("/api/books/{bookId}")
    public ResponseEntity<ApiResponse<Book>> getBook(@PathVariable Integer bookId) {
        Book book = bookService.getBookById(bookId);

        return ResponseEntity.ok(ApiResponse.success("成功取得書籍", book));
    }

    @PostMapping("/api/books")
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody @Valid BookRequest bookRequest) {
        Integer bookId = bookService.createBook(bookRequest);

        Book book = bookService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("新增成功", book));
    }

    @PutMapping("/api/books/{bookId}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@RequestBody @Valid BookRequest bookRequest,
                                           @PathVariable Integer bookId) {

        bookService.updateBook(bookId, bookRequest);

        Book updateBook = bookService.getBookById(bookId);

        return ResponseEntity.ok(ApiResponse.success("修改成功", updateBook));
    }

    @DeleteMapping("/api/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
