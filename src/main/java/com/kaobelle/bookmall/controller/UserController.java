package com.kaobelle.bookmall.controller;

import com.kaobelle.bookmall.dto.UserLoginRequest;
import com.kaobelle.bookmall.dto.UserRegisterRequest;
import com.kaobelle.bookmall.model.User;
import com.kaobelle.bookmall.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/users/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);
        System.out.println("Username: " + userRegisterRequest.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<User> login(@RequestBody UserLoginRequest userLoginRequest,
                                      HttpSession session) {
        User user = userService.login(userLoginRequest);

        if (user != null) {
            session.setAttribute("user", user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
