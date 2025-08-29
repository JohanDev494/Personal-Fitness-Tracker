package com.globant.repository;

import com.globant.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
//    void update(User user);
//    void delete(Long id);
//    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
}
