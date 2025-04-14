package com.kaobelle.bookmall.dao;

import com.kaobelle.bookmall.dto.UserRegisterRequest;
import com.kaobelle.bookmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
