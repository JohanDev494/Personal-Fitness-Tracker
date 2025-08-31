package com.globant.ui.impl;

import com.globant.model.*;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;

import java.time.LocalDateTime;
import java.util.*;

public class WorkoutLogUI {
    private final UserService userService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final ExerciseService exerciseService;
    private final SessionManager sessionManager;
    private final Scanner scanner;

    public WorkoutLogUI(
            UserService userService,
            WorkoutService workoutService,
            WorkoutLogService workoutLogService,
            ExerciseService exerciseService,
            SessionManager sessionManager,
            Scanner scanner)
    {
        this.userService = userService;
        this.workoutService = workoutService;
        this.workoutLogService = workoutLogService;
        this.exerciseService = exerciseService;
        this.sessionManager = sessionManager;
        this.scanner = scanner;
    }

    public void logWorkout(Workout workout) {
        Optional<User> currentUser = sessionManager.getCurrentUser();
        if (currentUser.isEmpty()) {
            System.out.println("⚠️ You must be logged in to log a workout.");
            return;
        }

        System.out.println("\n=== Logging Workout: " + workout.getName() + " ===");
        Map<String, Integer> timePerExercise = new HashMap<>();

        int totalTime = 0;
        for (WorkoutExercise we : workout.getWorkoutExercise()) {
            Exercise exercise = we.getExercise();
            System.out.print("Enter time (minutes) for " + exercise.getName() + ": ");
            int minutes = scanner.nextInt();
            scanner.nextLine();
            timePerExercise.put(exercise.getName(), minutes);
            totalTime += minutes;
        }
        System.out.println("Total time for " + workout.getName() + ": " + totalTime);

        String now = LocalDateTime.now().toString();
        WorkoutLog log = new WorkoutLog(
                currentUser.get(),
                now,
                workout,
                timePerExercise
        );

        workoutLogService.save(log);

        System.out.println("✅ Workout log saved!");
        new MainMenuUI(
                userService,
                exerciseService,
                workoutService,
                workoutLogService,
                sessionManager,
                scanner
        ).show();
    }
}
