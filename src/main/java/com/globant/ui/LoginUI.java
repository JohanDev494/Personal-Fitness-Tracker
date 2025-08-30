package com.globant.ui;

import com.globant.model.User;
import com.globant.service.UserService;

import com.globant.session.SessionManager;

import java.util.Optional;
import java.util.Scanner;

public class LoginUI extends BaseUI {

    public LoginUI(UserService userService, Scanner scanner, SessionManager sessionManager) {
        super(userService,sessionManager,scanner);
    }

    public void show() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        login(email, password);
    }

    private void login(String email, String password) {
        Optional<User> user = userService.login(email, password);

        if (user.isPresent()) {
            sessionManager.login(user.get());
            System.out.println("✅ Login successful! Welcome " + user.get().getName());

            if (sessionManager.hasRole("ADMIN")) {
                System.out.println("🔑 You are logged in as an admin.");
            }
        } else {
            System.out.println("⚠️ Invalid credentials.");
        }
    }
}
