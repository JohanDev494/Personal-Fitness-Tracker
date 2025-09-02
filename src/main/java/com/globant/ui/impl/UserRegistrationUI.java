package com.globant.ui.impl;

import com.globant.exception.InvalidInputException;
import com.globant.model.RegularUser;
import com.globant.service.UserService;
import com.globant.ui.UI;
import com.globant.util.InputHelper;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationUI implements UI {
    private final UserService userService;
    private final InputHelper inputHelper;

    private final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public UserRegistrationUI(UserService userService, InputHelper inputHelper) {
        this.userService = userService;
        this.inputHelper = inputHelper;
    }

    @Override
    public void show() {
        while (true) {
            try {
                System.out.println("\n===== Register =====");
                String name = promptName();
                String lastname = promptLastName();
                String email = promptEmail();
                String password = promptPassword();

                RegularUser regularUser = new RegularUser(name, lastname, email, password);

                userService.registerUser(regularUser);
                System.out.println("✅ User registered successfully!");
                return;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ Unexpected error during registration: " + e.getMessage());
            }
        }
    }

    // ==== Prompt methods ====

    private String promptName() {
        return inputHelper.readString("Enter name: ");
    }

    private String promptLastName() {
        return inputHelper.readString("Enter last name: ");
    }

    private String promptEmail() {
        String email;
        do {
            email = inputHelper.readString("Enter email: ");

            if (!email.matches(EMAIL_REGEX)) {
                System.out.println("⚠️ Invalid email format.");
                System.out.println("The email should be like: juanito@example.com");
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
            password = inputHelper.readString("Enter password: ");

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
