package com.cogent.banking.console.app.service;

import com.cogent.banking.console.app.entity.User;

public interface UserDao {
    User registerUser(User user);
    User login(String username, String password);
}
