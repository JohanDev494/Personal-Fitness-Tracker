package com.globant.ui;

import com.globant.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRegistrationUI {
    private final UserService userService;
    private final Scanner scanner;

    private final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public UserRegistrationUI(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public void show() {
        String name = promptName();
        String lastname = promptLastName();
        String email = promptEmail();
        String password = promptPassword();

        try {
            userService.registerUser(name, lastname, email, password);
            System.out.println("✅ User registered successfully!");
        } catch (Exception e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    // ==== Prompt methods ====

    private String promptName() {
        String name;
        do {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
            if (name.isBlank()) {
                System.out.println("⚠️ Name cannot be empty.");
            }
        } while (name.isBlank());
        return name;
    }

    private String promptLastName() {
        String lastname;
        do {
            System.out.print("Enter last name: ");
            lastname = scanner.nextLine();
            if (lastname.isBlank()) {
                System.out.println("⚠️ Last name cannot be empty.");
            }
        } while (lastname.isBlank());
        return lastname;
    }

    private String promptEmail() {
        String email;
        do {
            System.out.print("Enter email: ");
            email = scanner.nextLine();

            if (!email.matches(EMAIL_REGEX)) {
                System.out.println("⚠️ Invalid email format.");
                email = null;
            } else if (userService.findByEmail(email).isPresent()) {
                System.out.println("⚠️ This email is already registered.");
                email = null;
            }
        } while (email == null);
        return email;
    }

    private String promptPassword() {
        String password;
        do {
            System.out.print("Enter password: ");
            password = scanner.nextLine();

            List<String> passwordErrors = validatePassword(password);
            if (!passwordErrors.isEmpty()) {
                passwordErrors.forEach(err -> System.out.println("⚠️ " + err));
                password = null;
            }
        } while (password == null);
        return password;
    }

    // ==== Validation helpers ====

    private List<String> validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password.length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            errors.add("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            errors.add("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            errors.add("Password must contain at least one digit");
        }

        return errors;
    }
}
