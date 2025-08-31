package com.globant.ui.impl;

import com.globant.model.*;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.util.InputHelper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.time.format.DateTimeFormatter;

public class WorkoutLogUI {
    private final UserService userService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final ExerciseService exerciseService;
    private final SessionManager sessionManager;
    private final InputHelper inputHelper;
    private final DateTimeFormatter formatter;

    public WorkoutLogUI(
            UserService userService,
            WorkoutService workoutService,
            WorkoutLogService workoutLogService,
            ExerciseService exerciseService,
            SessionManager sessionManager,
            InputHelper inputHelper
    ) {
        this.userService = userService;
        this.workoutService = workoutService;
        this.workoutLogService = workoutLogService;
        this.exerciseService = exerciseService;
        this.sessionManager = sessionManager;
        this.inputHelper = inputHelper;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
            Integer minutes;
            do {
                try {
                    minutes = inputHelper.readInt(
                            "Enter time (minutes) for " + exercise.getName() + ": ",
                            0,
                            Integer.MAX_VALUE
                    );
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    minutes = null;
                } catch (Exception e) {
                    System.out.println("❌ Unexpected error during workout log: " + e.getMessage());
                    minutes = null;
                }
            } while (minutes == null);
            timePerExercise.put(exercise.getName(), minutes);
            totalTime += minutes;
        }

        System.out.println("Total time for " + workout.getName() + ": " + totalTime);

        String now = LocalDateTime.now().format(formatter);
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
                inputHelper
        ).show();
    }
}
