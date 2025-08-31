package com.globant.ui.impl;

import com.globant.model.User;
import com.globant.model.WorkoutLog;
import com.globant.service.WorkoutLogService;
import com.globant.session.SessionManager;
import com.globant.ui.UI;
import com.globant.util.InputHelper;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WorkoutLogListUI implements UI {
    private final WorkoutLogService workoutLogService;
    private final SessionManager sessionManager;
    private final InputHelper inputHelper;
    private final Scanner scanner;

    public WorkoutLogListUI(
            WorkoutLogService workoutLogService,
            SessionManager sessionManager,
            InputHelper inputHelper
    ) {
        this.workoutLogService = workoutLogService;
        this.sessionManager = sessionManager;
        this.inputHelper = inputHelper;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        User currentUser = sessionManager.getCurrentUser()
                .orElseThrow(() -> new IllegalStateException("No user logged in"));

        List<WorkoutLog> logs = workoutLogService.getLogsByUser(currentUser);

        if (logs.isEmpty()) {
            System.out.println("📭 No workouts logged yet!");
            return;
        }

        logs.sort(Comparator.comparing(WorkoutLog::getDate).reversed());

        while (true) {
            System.out.println("\n=== Your Logged Workouts ===");
            for (int i = 0; i < logs.size(); i++) {
                WorkoutLog log = logs.get(i);
                System.out.printf("%d. [%s] %s (Total time: %d mins)%n",
                        i + 1,
                        log.getDate(),
                        log.getWorkout().getName(),
                        log.getTotalTime()
                );
            }
            System.out.println("0. Back");

            try {
                int option = inputHelper.readInt("Select a workout to view details: ", 0, logs.size());
                if (option == 0) {
                    return;
                }
                showDetails(logs.get(option - 1));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Invalid option, try again.");
            }
        }
    }

    private void showDetails(WorkoutLog log) {
        System.out.println("\n=== Workout Log Details ===");
        System.out.println("Date: " + log.getDate());
        System.out.println("Workout: " + log.getWorkout().getName());
        System.out.println("Exercises:");
        log.getTimePerExercise().forEach((exercise, minutes) ->
                System.out.printf(" - %s: %d mins%n", exercise, minutes)
        );
        System.out.println("Total Time: " + log.getTotalTime() + " mins");
        System.out.println("\nPress ENTER to go back...");
        scanner.nextLine();
    }
}
