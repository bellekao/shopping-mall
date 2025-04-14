package com.kaobelle.bookmall.service;

import com.kaobelle.bookmall.dto.UserLoginRequest;
import com.kaobelle.bookmall.dto.UserRegisterRequest;
import com.kaobelle.bookmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);
}
