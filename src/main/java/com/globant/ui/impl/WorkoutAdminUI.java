package com.globant.ui.impl;

import com.globant.model.Exercise;
import com.globant.model.Workout;
import com.globant.model.WorkoutExercise;
import com.globant.service.ExerciseService;
import com.globant.service.WorkoutService;
import com.globant.ui.UI;
import com.globant.util.InputHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkoutAdminUI implements UI {
    private final WorkoutService workoutService;
    private final InputHelper inputHelper;
    private final Scanner scanner;
    private final ExerciseService exerciseService;

    public WorkoutAdminUI(WorkoutService workoutService, ExerciseService exerciseService ,InputHelper inputHelper, Scanner  scanner) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.inputHelper = inputHelper;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== Workout Administration ===");
            System.out.println("1. List Workouts");
            System.out.println("2. Create Workout");
            System.out.println("0. Back");
            try {
                int option = inputHelper.readInt("Choose an option: ", 0, 2);

                switch (option) {
                    case 1 -> listWorkouts();
                    case 2 -> createWorkout();
                    case 0 -> {
                        return;
                    }
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Invalid option, try again.");
            }
        }
    }
    private void listWorkouts() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        if (workouts.isEmpty()) {
            System.out.println("⚠️ No workouts available.");
            return;
        }
        System.out.println("\n=== Workouts ===");
        for (int i = 0; i < workouts.size(); i++) {
            System.out.println((i + 1) + ". " + workouts.get(i).getName());
        }
        System.out.println("Press ENTER to go back...");
        scanner.nextLine();
    }

    private void createWorkout() {
        String name = promptName();
        String description = promptDescription();
        String note = promptNote();

        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        boolean another;
        do {
            Exercise exercise = promptExercise();
            int sets = promptSets();
            int reps = promptReps();

            WorkoutExercise we = new WorkoutExercise(exercise);
            we.setSets(sets);
            we.setRepetitions(reps);

            workoutExercises.add(we);

            another = promptYesNo("Add another exercise? (y/n): ");

        } while (another);

        Workout workout = new Workout(name, workoutExercises, description, note);
        workoutService.createWorkout(workout);
        System.out.println("✅ Workout created successfully!");
    }

    private String promptName(){
        String name = null;
        do{
            try {
                name = inputHelper.readString("Enter workout name: ");
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }while (name == null);

        return name;
    }
    private String promptDescription() {
        String description = null;
        do {
            try {
                description = inputHelper.readString("Enter workout description: ");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (description == null);
        return description;
    }
    private String promptNote() {
        String note = null;
        do {
            try {
                note = inputHelper.readString("Enter workout note: ");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (note == null);
        return note;
    }
    private Exercise promptExercise() {
        Exercise exercise = null;
        do {
            try {
                List<Exercise> allExercises = exerciseService.getAllExercises();
                if (allExercises.isEmpty()) {
                    System.out.println("⚠️ No exercises available.");
                    return null;
                }

                System.out.println("\n=== Available Exercises ===");
                for (int i = 0; i < allExercises.size(); i++) {
                    System.out.println((i + 1) + ". " + allExercises.get(i).getName());
                }

                int option = inputHelper.readInt("Choose an exercise by number: ", 1, allExercises.size());
                exercise = allExercises.get(option - 1);

            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (exercise == null);

        return exercise;
    }
    private int promptSets() {
        Integer sets = null;
        do {
            try {
                sets = inputHelper.readInt("Enter number of sets: ", 1, 100);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (sets == null);
        return sets;
    }
    private int promptReps() {
        Integer reps = null;
        do {
            try {
                reps = inputHelper.readInt("Enter number of repetitions: ", 1, 500);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (reps == null);
        return reps;
    }
    private boolean promptYesNo(String message) {
        String input;
        do {
            input = inputHelper.readString(message).trim().toLowerCase();
        } while (!input.equals("y") && !input.equals("n"));
        return input.equals("y");
    }
}
