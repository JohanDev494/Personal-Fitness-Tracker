package com.globant.service;

import com.globant.model.User;

import java.util.Optional;

public interface UserService {
    void registerUser(User user);
    Optional<User> login(String email, String password);
    Optional<User> findByEmail(String Email);
}
