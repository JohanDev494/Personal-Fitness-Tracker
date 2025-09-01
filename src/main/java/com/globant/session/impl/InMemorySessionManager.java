package com.globant.session.impl;

import com.globant.model.User;
import com.globant.session.SessionManager;

import java.util.Optional;

public class InMemorySessionManager implements SessionManager {
    private User currentUser;

    @Override
    public void login(User user) {
        this.currentUser = user;
    }

    @Override
    public void logout() {
        this.currentUser = null;
    }

    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    @Override
    public boolean hasRole(String role) {
        return currentUser != null && role.equalsIgnoreCase(currentUser.getRol());
    }
}