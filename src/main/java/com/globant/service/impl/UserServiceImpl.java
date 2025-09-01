package com.globant.service.impl;

import com.globant.model.User;
import com.globant.repository.UserRepository;
import com.globant.service.UserService;

import java.util.Optional;

public record UserServiceImpl(UserRepository userRepository) implements UserService {

    @Override
    public void registerUser(User user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("The email entered is already registered");
        }
        userRepository.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user;
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
