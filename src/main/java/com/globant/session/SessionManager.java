package com.globant.session;

import com.globant.model.User;

import java.util.Optional;

public interface SessionManager {
    void login(User user);
    void logout();
    Optional<User> getCurrentUser();
    boolean isLoggedIn();
    boolean hasRole(String role);
}
