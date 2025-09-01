package com.globant.repository.impl;

import com.globant.model.User;
import com.globant.repository.UserRepository;
import com.globant.storage.Storage;

import java.util.List;
import java.util.Optional;

public class UserFileRepository implements UserRepository {

    private final Storage<User> storage;
    private List<User> users;

    public UserFileRepository(Storage<User> storage) {
        this.storage = storage;
        this.users = storage.load();
    }

    @Override
    public void save(User user) {
        users.add(user);
        storage.save(users);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
