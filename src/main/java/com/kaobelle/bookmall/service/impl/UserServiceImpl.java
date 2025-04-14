package com.kaobelle.bookmall.service.impl;

import com.kaobelle.bookmall.dao.UserDao;
import com.kaobelle.bookmall.dto.UserLoginRequest;
import com.kaobelle.bookmall.dto.UserRegisterRequest;
import com.kaobelle.bookmall.model.User;
import com.kaobelle.bookmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查該 email 是否已被註冊過
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("該 Email:{} 已經被註冊過", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "註冊失敗，該電子郵件已經被註冊過");
        }

        // 註冊
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查 user 是否存在
        if (user == null) {
            log.warn("該 Email:{} 尚未被註冊過", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "登入失敗，該電子郵件尚未被註冊過");
        }

        // 檢查 email 和密碼是否正確
        if (userLoginRequest.getPassword().equals(user.getPassword())) {
            return user;
        } else {
            log.warn("Email 或密碼輸入錯誤");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "登入失敗，請檢查電子郵件或密碼");
        }
    }
}
