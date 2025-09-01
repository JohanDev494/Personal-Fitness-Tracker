package com.globant.ui.impl;

import com.globant.model.Exercise;
import com.globant.service.ExerciseService;
import com.globant.ui.UI;
import com.globant.util.InputHelper;

import java.util.List;
import java.util.Scanner;

public class ExerciseAdminUI implements UI {
    private final ExerciseService exerciseService;
    private final InputHelper inputHelper;
    private final Scanner scanner;

    public ExerciseAdminUI(ExerciseService exerciseService, InputHelper inputHelper, Scanner scanner) {
        this.exerciseService = exerciseService;
        this.inputHelper = inputHelper;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== Exercise Administration ===");
            System.out.println("1. List Exercises");
            System.out.println("2. Create Exercise");
            System.out.println("3. Update Exercise");
            System.out.println("4. Delete Exercise");
            System.out.println("0. Back");
            try{
                int choice = inputHelper.readInt("Choose an option: ", 0, 4);
                switch (choice) {
                    case 1 -> listExercises();
                    case 2 -> createExercise();
                    case 3 -> updateExercise();
                    case 4 -> deleteExercise();
                    case 0 -> {
                        return;
                    }
                }
            }catch (RuntimeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Invalid option, try again.");
            }
        }
    }

    private void listExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        if (exercises.isEmpty()) {
            System.out.println("⚠️ No exercises found.");
            return;
        }

        System.out.println("\n=== Available Exercises ===");
        for (int i = 0; i < exercises.size(); i++) {
            Exercise ex = exercises.get(i);
            System.out.println((i + 1) + ". " + ex.getName());
        }
        System.out.println("Press ENTER to go back...");
        scanner.nextLine();
    }

    private void createExercise() {
        System.out.println("\n=== Create Exercise ===");
        String name = promptWithMessage("Enter exercise name: ");

        Exercise exercise = new Exercise(name);
        exerciseService.createExercise(exercise);

        System.out.println("✅ Exercise created!");
    }

    private void updateExercise() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        if (exercises.isEmpty()) {
            System.out.println("⚠️ No exercises available to update.");
            return;
        }

        System.out.println("\n=== Select Exercise to Update ===");
        for (int i = 0; i < exercises.size(); i++) {
            System.out.println((i + 1) + ". " + exercises.get(i).getName());
        }

        int choice = promptChoiceExercise(exercises.size());
        Exercise existing = exercises.get(choice - 1);
        String oldName = existing.getName();

        String newName = promptWithMessage("Enter new name: ");

        Exercise updatedExercise = new Exercise(newName);

        if (exerciseService.updateExercise(oldName, updatedExercise)) {
            System.out.println("✅ Exercise updated!");
        } else {
            System.out.println("❌ Error updating exercise.");
        }
    }

    private void deleteExercise() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        if (exercises.isEmpty()) {
            System.out.println("⚠️ No exercises available to delete.");
            return;
        }

        System.out.println("\n=== Select Exercise to Delete ===");
        for (int i = 0; i < exercises.size(); i++) {
            System.out.println((i + 1) + ". " + exercises.get(i).getName());
        }

        int choice = promptChoiceExercise(exercises.size());
        Exercise selected = exercises.get(choice - 1);

        boolean confirm = promptYesNo("Are you sure you want to delete '" + selected.getName() + "'? (y/n): ");

        if (!confirm) {
            System.out.println("❌ Deletion cancelled.");
            return;
        }

        if (exerciseService.deleteExercise(selected.getName())) {
            System.out.println("✅ Exercise '" + selected.getName() + "' deleted!");
        } else {
            System.out.println("❌ Error: exercise not found.");
        }
    }

    // === PROMPTS ===
    private int promptChoiceExercise(int size) {
        Integer choice = null;
        do {
            try {
                choice = inputHelper.readInt("Choose a exercise (1-" + size + "): ", 1, size);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (choice == null);
        return choice;
    }
    private String promptWithMessage(String message) {
        String name = null;
        do{
            try {
                name = inputHelper.readString(message);
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }while (name == null);

        return name;
    }
    private boolean promptYesNo(String message) {
        String input;
        do {
            input = inputHelper.readString(message).trim().toLowerCase();
        } while (!input.equals("y") && !input.equals("n"));
        return input.equals("y");
    }
}
