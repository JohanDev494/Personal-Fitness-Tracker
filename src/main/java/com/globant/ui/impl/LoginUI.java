package com.globant.ui.impl;

import com.globant.exception.InvalidInputException;
import com.globant.model.User;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.ui.UI;
import com.globant.util.InputHelper;

import java.util.Optional;
import java.util.Scanner;

public class LoginUI implements UI {
    private final UserService userService;
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final InputHelper inputHelper;
    private final SessionManager sessionManager;
    private final Scanner scanner;
    public LoginUI(
            UserService userService,
            ExerciseService exerciseService,
            WorkoutService workoutService,
            WorkoutLogService workoutLogService,
            InputHelper inputHelper,
            SessionManager sessionManager,
            Scanner scanner
    ) {
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.workoutService = workoutService;
        this.workoutLogService = workoutLogService;
        this.inputHelper = inputHelper;
        this.sessionManager = sessionManager;
        this.scanner = scanner;
    }

    @Override
    public void show() {
        try {
            System.out.println("\n===== Login =====");
            String email = inputHelper.readString("Enter email: ");
            String password = inputHelper.readString("Enter password: ");

            login(email, password);

        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error during login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void login(String email, String password) {
        Optional<User> user = userService.login(email, password);

        if (user.isPresent()) {
            sessionManager.login(user.get());
            System.out.println("✅ Login successful! Welcome " + user.get().getName());

            if (sessionManager.hasRole("ADMIN")) {
                System.out.println("🔑 You are logged in as an admin.");
            }

            new MainMenuUI(
                    userService,
                    exerciseService,
                    workoutService,
                    workoutLogService,
                    sessionManager,
                    inputHelper,
                    scanner
            ).show();
        } else {
            System.out.println("⚠️ Invalid credentials.");
        }
    }
}
