package com.kaobelle.bookmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

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
}
