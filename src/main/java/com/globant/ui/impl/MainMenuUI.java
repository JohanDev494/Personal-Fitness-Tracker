package com.globant.ui.impl;

import com.globant.model.AdminUser;
import com.globant.model.RegularUser;
import com.globant.model.User;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.ui.UI;
import com.globant.ui.impl.WorkoutUI;
import com.globant.ui.abst.BaseUI;

import java.util.Scanner;

public class MainMenuUI implements UI {
    private UserService userService;
    private ExerciseService exerciseService;
    private WorkoutService workoutService;
    private WorkoutLogService workoutLogService;
    private SessionManager sessionManager;
    private Scanner scanner;
    public MainMenuUI(
            UserService userService,
            ExerciseService exerciseService,
            WorkoutService workoutService,
            WorkoutLogService workoutLogService,
            SessionManager sessionManager,
            Scanner scanner)
    {
        this.exerciseService = exerciseService;
        this.workoutService = workoutService;
        this.workoutLogService = workoutLogService;
        this.userService = userService;
        this.sessionManager = sessionManager;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            User currentUser = sessionManager.getCurrentUser()
                    .orElseThrow(() -> new IllegalStateException("No user logged in"));

            System.out.println("\n==== Main Menu ====");
            System.out.println("Welcome, " + currentUser.getName() + " (" + currentUser.getRol() + ")");

            if (currentUser.getRol().equals("ADMIN")) {
                adminMenu();
            } else if (currentUser.getRol().equals("USER")) {
                regularUserMenu();
            }

            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 0) {
                sessionManager.logout();
                System.out.println("👋 Logged out successfully!");
                return;
            } else {
                handleOption(option, currentUser);
            }
        }
    }

    private void adminMenu() {
        System.out.println("1. Manage Users");
        System.out.println("2. View System Logs");
    }

    private void regularUserMenu() {
        System.out.println("1. View Available Workouts");
        System.out.println("2. View Logged Workouts");
    }

    private void handleOption(int option, User user) {
        if (user.getRol().equals("ADMIN")) {
            switch (option) {
                case 1 -> System.out.println("🔧 Managing users...");
                case 2 -> System.out.println("📜 Viewing system logs...");
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
                        scanner).show();
                case 2 -> System.out.println("📈 View progress...");
                default -> System.out.println("⚠️ Invalid option");
            }
        }
    }
}
