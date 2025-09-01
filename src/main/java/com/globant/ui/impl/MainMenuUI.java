package com.globant.ui.impl;

import com.globant.model.User;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.ui.UI;
import com.globant.util.InputHelper;

import java.util.Scanner;

public class MainMenuUI implements UI {
    private final UserService userService;
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final SessionManager sessionManager;
    private final InputHelper inputHelper;
    private final Scanner scanner;

    public MainMenuUI(
            UserService userService,
            ExerciseService exerciseService,
            WorkoutService workoutService,
            WorkoutLogService workoutLogService,
            SessionManager sessionManager,
            InputHelper inputHelper,
            Scanner scanner
    ) {
        this.exerciseService = exerciseService;
        this.workoutService = workoutService;
        this.workoutLogService = workoutLogService;
        this.userService = userService;
        this.sessionManager = sessionManager;
        this.inputHelper = inputHelper;
        this.scanner = scanner;
    }

    @Override
    public void show() {
        while (true) {
            User currentUser = sessionManager.getCurrentUser()
                    .orElseThrow(() -> new IllegalStateException("No user logged in"));

            System.out.println("\n==== Main Menu ====");
            System.out.println("Welcome, " + currentUser.getName() + " (" + currentUser.getRol() + ")");

            String userRol = currentUser.getRol();
            if (userRol.equals("ADMIN")) {
                adminMenu();
            } else if (userRol.equals("USER")) {
                regularUserMenu();
            }

            System.out.println("0. Logout");
            try {
                int option = inputHelper.readInt("Choose an option: ", 0, 2);
                if (option == 0) {
                    sessionManager.logout();
                    System.out.println("👋 Logged out successfully!");
                    return;
                } else {
                    handleOption(option, currentUser);
                }
            }catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }catch (Exception e) {
                System.out.println("⚠️ Invalid option, try again.");            }
        }
    }

    private void adminMenu() {
        System.out.println("1. Manage Workouts");
        System.out.println("2. Manage Exercises");
    }

    private void regularUserMenu() {
        System.out.println("1. View Available Workouts");
        System.out.println("2. View Logged Workouts");
    }

    private void handleOption(int option, User user) {
        if (user.getRol().equals("ADMIN")) {
            switch (option) {
                case 1 -> new WorkoutAdminUI(
                        workoutService,
                        exerciseService,
                        inputHelper,
                        scanner
                    ).show();
                case 2 -> new ExerciseAdminUI(
                        exerciseService,
                        inputHelper,
                        scanner
                    ).show();
                default -> System.out.println("⚠️ Invalid option");
            }
        } else if (user.getRol().equals("USER")) {
            switch (option) {
                case 1 -> new WorkoutUI(
                        userService,
                        workoutService,
                        workoutLogService,
                        exerciseService,
                        sessionManager,
                        inputHelper,
                        scanner
                    ).show();
                case 2 -> new WorkoutLogListUI(
                        workoutLogService,
                        sessionManager,
                        inputHelper
                    ).show();
                default -> System.out.println("⚠️ Invalid option");
            }
        }
    }
}
