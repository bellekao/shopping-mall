package com.kaobelle.bookmall.controller;

import com.kaobelle.bookmall.dto.ApiResponse;
import com.kaobelle.bookmall.dto.UserLoginRequest;
import com.kaobelle.bookmall.dto.UserRegisterRequest;
import com.kaobelle.bookmall.model.User;
import com.kaobelle.bookmall.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/users/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("新增成功", user));
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody @Valid UserLoginRequest userLoginRequest,
                                      HttpSession session) {
        User user = userService.login(userLoginRequest);

        if (user != null) {
            session.setAttribute("user", user);
            return ResponseEntity.ok(ApiResponse.success("登入成功", user));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "帳號或密碼錯誤");
        }
    }
}
