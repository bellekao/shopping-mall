package com.kaobelle.bookmall.controller;

import com.kaobelle.bookmall.constant.BookCategory;
import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final BookService bookService;

    public PageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    // 顯示登入頁面
    @GetMapping("/users/login")
    public String loginPage(Model model) {
        return "users/login";
    }

    // 顯示註冊頁面
    @GetMapping("/users/register")
    public String registerPage(Model model) {
        return "users/register";
    }

    // 顯示管理員的書籍管理頁面
    @GetMapping("/managers/book_manager")
    public String bookManagerPage(Model model) {
        return "/managers/book_manager";
    }

    // 顯示 book_edit 頁面（新增或修改）
    @GetMapping("/managers/book_edit")
    public String showBookEditPage(@RequestParam(value = "bookId", required = false) Integer bookId,
                                   Model model) {
        if (bookId != null) {
            Book book = bookService.getBookById(bookId);
            model.addAttribute("isEdit", true);
            model.addAttribute("book", book);
            model.addAttribute("categoryOptions", BookCategory.values());

        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("isEdit", null); // 明確傳 null 讓 th:if 可判斷，表示是新增
            model.addAttribute("categoryOptions", BookCategory.values());
        }
        return "managers/book_edit";
    }
}
