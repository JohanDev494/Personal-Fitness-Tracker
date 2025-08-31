package com.globant.ui.impl;

import com.globant.model.Exercise;
import com.globant.model.User;
import com.globant.model.Workout;
import com.globant.model.WorkoutExercise;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.session.SessionManager;
import com.globant.ui.UI;

import java.util.List;
import java.util.Scanner;

public class WorkoutUI implements UI {
    private final UserService userService;
    private final WorkoutService workoutService;
    private final WorkoutLogService workoutLogService;
    private final ExerciseService exerciseService;
    private final SessionManager sessionManager;
    private final Scanner scanner;

    public WorkoutUI(
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

    public void show() {
        System.out.println("\n=== Workout Menu ===");
        listWorkouts();
    }

    private void listWorkouts() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        if (workouts.isEmpty()) {
            System.out.println("No workouts available.");
            return;
        }

        System.out.println("=== Workouts ===");
        int index = 1;
        for (Workout workout : workouts) {
            System.out.println(index + ". " + workout.getName() + " - " + workout.getDescription());
            index++;
        }

        int option = -1;
        do {
            System.out.print("Select a workout to see the details or 0 to back: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("⚠️ Please enter a number.");
                continue;
            }

            try {
                option = Integer.parseInt(input);
                if (option == 0) {
                    break;
                } else if (option > 0 && option <= workouts.size()) {
                    detailsWorkout(workouts.get(option - 1));
                } else {
                    System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
            }
        } while (option != 0);
    }


    private void detailsWorkout(Workout workout) {
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
        System.out.println("Enter 1 for log this workout or 0 for back: ");
        int option = Integer.parseInt(scanner.nextLine());

        if (option == 1) {
            new WorkoutLogUI(
                    userService,
                    workoutService,
                    workoutLogService,
                    exerciseService,
                    sessionManager,
                    scanner).logWorkout(workout);
            return;
        }
    }

//    private void logWorkout(Workout workout) {
//        Optional<User> currentUser = sessionManager.getCurrentUser();
//        if (currentUser == null) {
//            System.out.println("⚠️ You must be logged in to log a workout.");
//            return;
//        }
//
//        System.out.println("\n=== Logging Workout: " + workout.getName() + " ===");
//
//        int totalTime = 0;
//        List<WorkoutLogExercise> logExercises = new ArrayList<>();
//
//        for (WorkoutExercise we : workout.getWorkoutExercise()) {
//            System.out.print("Enter time taken (in seconds) for " + we.getExercise().getName() + ": ");
//            int time = Integer.parseInt(scanner.nextLine());
//
//            totalTime += time;
//
//            logExercises.add(new WorkoutLogExercise(
//                    we.getExercise(),
//                    we.getSets(),
//                    we.getRepetitions(),
//                    time
//            ));
//        }
//
//        WorkoutLog log = new WorkoutLog(
//                currentUser,
//                workout.getName(),
//                LocalDateTime.now(),
//                totalTime,
//                logExercises
//        );
//
//        workoutLogService.save(log);
//
//        System.out.println("✅ Workout logged successfully!");
//        System.out.println("Total time: " + totalTime + " seconds");
//    }
//    private void createWorkout() {
//        if (!sessionManager.getCurrentUser().isAdmin()) {
//            System.out.println("Only admins can create workouts.");
//            return;
//        }
//
//        System.out.print("Enter workout name: ");
//        String name = scanner.nextLine();
//
//        System.out.print("Enter workout description: ");
//        String description = scanner.nextLine();
//
//        System.out.print("Enter workout note (optional): ");
//        String note = scanner.nextLine();
//
//        // Selección de ejercicios
//        List<Exercise> allExercises = exerciseService.getAll();
//        if (allExercises.isEmpty()) {
//            System.out.println("No exercises available. Please create exercises first.");
//            return;
//        }
//
//        List<WorkoutExercise> selectedExercises = new ArrayList<>();
//        System.out.println("Available exercises:");
//        for (int i = 0; i < allExercises.size(); i++) {
//            System.out.printf("%d. %s%n", i + 1, allExercises.get(i).getName());
//        }
//
//        System.out.println("Select exercises for this workout (comma separated, e.g., 1,3,4): ");
//        String[] selections = scanner.nextLine().split(",");
//
//        for (String sel : selections) {
//            try {
//                int index = Integer.parseInt(sel.trim()) - 1;
//                if (index >= 0 && index < allExercises.size()) {
//                    Exercise ex = allExercises.get(index);
//                    WorkoutExercise we = new WorkoutExercise(ex);
//
//                    System.out.printf("Enter sets for %s: ", ex.getName());
//                    int sets = Integer.parseInt(scanner.nextLine());
//
//                    System.out.printf("Enter repetitions for %s: ", ex.getName());
//                    int reps = Integer.parseInt(scanner.nextLine());
//
//                    we.setSets(sets);
//                    we.setRepetitions(reps);
//
//                    selectedExercises.add(we);
//                }
//            } catch (NumberFormatException ignored) {
//            }
//        }
//
//        if (selectedExercises.isEmpty()) {
//            System.out.println("No exercises selected. Workout creation cancelled.");
//            return;
//        }
//
//        Workout workout = new Workout(name, selectedExercises, description, note);
//        workoutService.createWorkout(workout);
//
//        System.out.println("Workout created successfully!");
//    }
}
