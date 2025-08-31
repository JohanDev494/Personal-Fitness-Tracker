package com.globant.ui.impl;

import com.globant.model.User;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;

import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.ui.UI;

import java.util.Optional;
import java.util.Scanner;

public class LoginUI implements UI {
    private final UserService userService;
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public LoginUI(
            UserService userService,
            ExerciseService exerciseService,
            WorkoutService workoutService,
            WorkoutLogService workoutLogService,
            Scanner scanner,
            SessionManager sessionManager
    ) {
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.workoutService = workoutService;
        this.workoutLogService = workoutLogService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
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

            new MainMenuUI(
                    userService,
                    exerciseService,
                    workoutService,
                    workoutLogService,
                    sessionManager,
                    scanner).show();
        } else {
            System.out.println("⚠️ Invalid credentials.");
        }
    }
}
