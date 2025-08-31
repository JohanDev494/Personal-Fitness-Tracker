package com.globant.ui;

import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.ui.impl.LoginUI;
import com.globant.ui.impl.UserRegistrationUI;

import java.util.Scanner;

public class ConsoleUI {
    private final UserService userService;
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final SessionManager sessionManager;
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
    }

    public void start() {
        while (true) {
            System.out.println("\n==== Welcome to the Personal Fitness Tracker ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> new UserRegistrationUI(userService, scanner).show();
                case 2 -> new LoginUI(
                        userService,
                        exerciseService,
                        workoutService,
                        workoutLogService,
                        scanner,
                        sessionManager).show();
                case 3 -> {
                    System.out.println("exit");
                    return;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }
}
