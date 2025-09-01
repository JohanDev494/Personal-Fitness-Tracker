package com.globant.ui.impl;

import com.globant.model.Workout;
import com.globant.model.WorkoutExercise;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.ui.UI;
import com.globant.util.InputHelper;

import java.util.List;
import java.util.Scanner;

public class WorkoutUI implements UI {
    private final UserService userService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final ExerciseService exerciseService;
    private final SessionManager sessionManager;
    private final InputHelper inputHelper;
    private final Scanner scanner;

    public WorkoutUI(
            UserService userService,
            WorkoutService workoutService,
            WorkoutLogService workoutLogService,
            ExerciseService exerciseService,
            SessionManager sessionManager,
            InputHelper inputHelper,
            Scanner scanner
    )
    {
        this.userService = userService;
        this.workoutService = workoutService;
        this.workoutLogService = workoutLogService;
        this.exerciseService = exerciseService;
        this.sessionManager = sessionManager;
        this.inputHelper = inputHelper;
        this.scanner = scanner;
    }

    public void show() {
        listWorkouts();
    }

    private void listWorkouts() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        if (workouts.isEmpty()) {
            System.out.println("⚠️ No workouts available.");
            return;
        }
        while (true){
            System.out.println("\n=== Workouts ===");
            for (int i = 0; i < workouts.size(); i++) {
                Workout workout = workouts.get(i);
                System.out.printf("%d. %s - %s%n", i + 1, workout.getName(), workout.getDescription());
            }

            try {
                int option = inputHelper.readInt("Select a workout (0 to back): ", 0, workouts.size());
                if (option == 0) {
                    return;
                }
                detailsWorkout(workouts.get(option - 1));
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println("⚠️ Invalid option, try again.");
            }
        }
    }


    private void detailsWorkout(Workout workout) {
        while(true) {
            System.out.println("\n=== Workout Details ===");
            System.out.println("Name: " + workout.getName());
            System.out.println("Description: " + workout.getDescription());
            if (workout.getNote() != null && !workout.getNote().isBlank()) {
                System.out.println("Note: " + workout.getNote());
            }
            System.out.println("Exercises:");
            for (WorkoutExercise we : workout.getWorkoutExercise()) {
                System.out.printf(" - %s (Sets: %d, Reps: %d)%n",
                        we.getExercise().getName(), we.getSets(), we.getRepetitions());
            }
            System.out.println("\n1. Log this workout.");
            System.out.println("0. Back.");
            try {
                int option = inputHelper.readInt("Choose option: ", 0, 1);

                if (option == 0 ) {
                    return;
                }

                if (option == 1) {
                    new WorkoutLogUI(
                            userService,
                            workoutService,
                            workoutLogService,
                            exerciseService,
                            sessionManager,
                            inputHelper,
                            scanner
                    ).logWorkout(workout);
                }
            }catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }catch (Exception e) {
                System.out.println("⚠️ Invalid option, try again.");
            }
        }
    }
}
