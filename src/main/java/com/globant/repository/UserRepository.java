package com.globant.repository;

import com.globant.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
}
