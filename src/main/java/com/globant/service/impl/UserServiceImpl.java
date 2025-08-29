package com.globant.service.impl;

import com.globant.model.User;
import com.globant.repository.UserRepository;
import com.globant.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User  user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("The email entered is already registered"); // Error de registro
        }

        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
//    public List<User> listUsers() {
//        return userRepository.findAll();
//    }

//    @Override
//    public void deleteUser(Long id) {
//        userRepository.delete(id);
//    }
}
