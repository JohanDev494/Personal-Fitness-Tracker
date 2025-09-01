package com.globant.ui;

import com.globant.exception.InvalidInputException;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.util.InputHelper;
import com.globant.ui.impl.LoginUI;
import com.globant.ui.impl.UserRegistrationUI;

import java.util.Scanner;

public class ConsoleUI {
    private final UserService userService;
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final SessionManager sessionManager;
    private final InputHelper inputHelper;
    private final Scanner scanner;

    public ConsoleUI(
            UserService userService,
            ExerciseService exerciseService,
            WorkoutService workoutService,
            WorkoutLogService workoutLogService,
            SessionManager sessionManager)
    {
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.workoutService = workoutService;
        this.workoutLogService = workoutLogService;
        this.sessionManager = sessionManager;
        this.scanner = new Scanner(System.in);
        this.inputHelper = new InputHelper(scanner);
    }

    public void start() {
        while (true) {
            try {
                System.out.println("\n==== Welcome to the Personal Fitness Tracker ====");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");

                int option = inputHelper.readInt("Please select an option: ", 1, 3);

                switch (option) {
                    case 1 -> new UserRegistrationUI(
                            userService,
                            inputHelper
                        ).show();
                    case 2 -> new LoginUI(
                            userService,
                            exerciseService,
                            workoutService,
                            workoutLogService,
                            inputHelper,
                            sessionManager,
                            scanner
                    ).show();
                    case 3 -> {
                        System.out.println("👋 Exiting the system. Goodbye!");
                        return;
                    }
                    default -> System.out.println("⚠️ Invalid option, try again.");
                }

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("❌ Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
