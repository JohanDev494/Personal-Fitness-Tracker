package com.globant.service;

import com.globant.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(String name, String lastname, String email, String password);
//    Optional<User> findById(Long id);
    Optional<User> findByEmail(String Email);
//    List<User> listUsers();
//    void deleteUser(Long id);
}
