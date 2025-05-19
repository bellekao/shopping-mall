package com.kaobelle.bookmall.controller;

import com.kaobelle.bookmall.constant.BookCategory;
import com.kaobelle.bookmall.dto.BookQueryParams;
import com.kaobelle.bookmall.model.Book;
import com.kaobelle.bookmall.model.Order;
import com.kaobelle.bookmall.model.User;
import com.kaobelle.bookmall.service.BookService;
import com.kaobelle.bookmall.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    public PageController(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public PageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        BookQueryParams bookQueryParams = new BookQueryParams();
        bookQueryParams.setCategory(null);
        bookQueryParams.setSearch(null);
        bookQueryParams.setOrderBy("book_id");
        bookQueryParams.setSort("asc");

        List<Book> books = bookService.getBooks(bookQueryParams);
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/books/{bookId}")
    public String bookDetailPage(@PathVariable Integer bookId, Model model) {
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "books/book_detail";
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
            model.addAttribute("isEdit", null); // 傳 null 讓 th:if 可判斷，表示是新增
            model.addAttribute("categoryOptions", BookCategory.values());
        }
        return "managers/book_edit";
    }

    @GetMapping("/carts/cart")
    public String cartPage() {
        return "carts/cart";
    }

    @GetMapping("/orders/order")
    public String orderPage() {
        return "orders/order";
    }

    // 顯示訂單詳細頁面
    @GetMapping("/orders/{orderId}")
    public String getOrderDetailPage(@PathVariable Integer orderId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login";  // 未登入的情況下，跳轉至登入頁面
        }

        // 呼叫 Service 層來取得訂單詳情
        Order order = orderService.getOrderDetail(user.getUserId(), orderId);
        model.addAttribute("order", order);  // 將訂單資料放進 model 中傳遞到前端
        return "orders/order_detail";  // 返回訂單詳細頁面的 Thymeleaf 視圖名稱
    }

}
