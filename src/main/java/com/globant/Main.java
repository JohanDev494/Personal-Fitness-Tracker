package com.globant;

import com.globant.model.*;
import com.globant.repository.ExerciseRepository;
import com.globant.repository.UserRepository;
import com.globant.repository.WorkoutLogRepository;
import com.globant.repository.WorkoutRepository;
import com.globant.repository.impl.ExerciseFileRepository;
import com.globant.repository.impl.UserFileRepository;
import com.globant.repository.impl.WorkoutFileRepository;
import com.globant.repository.impl.WorkoutLogFileRepository;
import com.globant.service.ExerciseService;
import com.globant.service.UserService;
import com.globant.service.WorkoutLogService;
import com.globant.service.WorkoutService;
import com.globant.service.impl.ExerciseServiceImpl;
import com.globant.service.impl.UserServiceImpl;
import com.globant.service.impl.WorkoutLogServiceImpl;
import com.globant.service.impl.WorkoutServiceImpl;
import com.globant.session.impl.InMemorySessionManager;
import com.globant.session.SessionManager;
import com.globant.storage.Storage;
import com.globant.storage.impl.JsonFileStorage;
import com.globant.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        Storage<User> userStorage = new JsonFileStorage<>("users.json", User.class);
        Storage<Exercise> exerciseStorage = new JsonFileStorage<>("exercises.json", Exercise.class);
        Storage<Workout> workoutStorage = new JsonFileStorage<>("workouts.json", Workout.class);
        Storage<WorkoutLog> workoutLogStorage = new JsonFileStorage<>("workoutLogs.json", WorkoutLog.class);

        UserRepository userRepository = new UserFileRepository(userStorage);
        ExerciseRepository exerciseRepository = new ExerciseFileRepository(exerciseStorage);
        WorkoutRepository workoutRepository = new WorkoutFileRepository(workoutStorage);
        WorkoutLogRepository workoutLogRepository = new WorkoutLogFileRepository(workoutLogStorage);

        UserService userService = new UserServiceImpl(userRepository);
        ExerciseService exerciseService = new ExerciseServiceImpl(exerciseRepository);
        WorkoutService workoutService = new WorkoutServiceImpl(workoutRepository);
        WorkoutLogService workoutLogService = new WorkoutLogServiceImpl(workoutLogRepository);

        SessionManager sessionManager = new InMemorySessionManager();

//        createDefaultAdmin(userService, userRepository);
        createDefaultExercises(exerciseRepository);
        createDefaultWorkouts(workoutRepository, exerciseRepository);

        ConsoleUI ui = new ConsoleUI(
                userService,
                exerciseService,
                workoutService,
                workoutLogService,
                sessionManager);

        ui.start();
    }

    private static void createDefaultAdmin(UserService userService, UserRepository userRepository) {
        String adminEmail = "admin@system.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
        System.out.println("Creating default admin");
            AdminUser admin = new AdminUser(
                    "Admin",
                    "System",
                    adminEmail,
                    "Admin123"
            );
            userRepository.save(admin);
            System.out.println("⚙️ Default admin user created: " + adminEmail);

        }
    }

    private static void createDefaultExercises(ExerciseRepository repo) {
        if (repo.getAll().isEmpty()) {
            System.out.println("⚙️ Creating default exercises...");
            repo.save(new Exercise("Push Ups"));
            repo.save(new Exercise("Squats"));
            repo.save(new Exercise("Plank"));
            repo.save(new Exercise("Lunges"));
            repo.save(new Exercise("Burpees"));
            System.out.println("✅ Default exercises created");
        }
    }


    private static void createDefaultWorkouts(WorkoutRepository workoutRepo, ExerciseRepository exerciseRepo) {
        if (workoutRepo.getAll().isEmpty()) {
            System.out.println("⚙️ Creating default workouts...");

            Exercise pushUps = exerciseRepo.getAll().stream()
                    .filter(e -> e.getName().equalsIgnoreCase("Push Ups"))
                    .findFirst().orElse(null);

            Exercise squats = exerciseRepo.getAll().stream()
                    .filter(e -> e.getName().equalsIgnoreCase("Squats"))
                    .findFirst().orElse(null);

            Exercise plank = exerciseRepo.getAll().stream()
                    .filter(e -> e.getName().equalsIgnoreCase("Plank"))
                    .findFirst().orElse(null);

            Exercise lunges = exerciseRepo.getAll().stream()
                    .filter(e -> e.getName().equalsIgnoreCase("Lunges"))
                    .findFirst().orElse(null);

            Exercise burpees = exerciseRepo.getAll().stream()
                    .filter(e -> e.getName().equalsIgnoreCase("Burpees"))
                    .findFirst().orElse(null);

            if (pushUps != null && squats != null && plank != null && lunges != null && burpees != null) {
                WorkoutExercise we1 = new WorkoutExercise(pushUps);
                we1.setSets(3);
                we1.setRepetitions(12);

                WorkoutExercise we2 = new WorkoutExercise(squats);
                we2.setSets(3);
                we2.setRepetitions(15);

                WorkoutExercise we3 = new WorkoutExercise(plank);
                we3.setSets(3);
                we3.setRepetitions(60);

                Workout workout1 = new Workout(
                        "Beginner Full Body",
                        java.util.List.of(we1, we2, we3),
                        "Simple full body workout",
                        "Do 3 rounds"
                );

                WorkoutExercise we4 = new WorkoutExercise(lunges);
                we4.setSets(3);
                we4.setRepetitions(10);

                WorkoutExercise we5 = new WorkoutExercise(pushUps);
                we5.setSets(3);
                we5.setRepetitions(15);

                Workout workout2 = new Workout(
                        "Lower Body Blast",
                        java.util.List.of(we4, we2, we5), // squats reusado
                        "Focus on legs and endurance",
                        "Keep good form"
                );

                WorkoutExercise we6 = new WorkoutExercise(burpees);
                we6.setSets(3);
                we6.setRepetitions(12);

                WorkoutExercise we7 = new WorkoutExercise(plank);
                we7.setSets(3);
                we7.setRepetitions(45);

                Workout workout3 = new Workout(
                        "Cardio & Core",
                        java.util.List.of(we6, we7),
                        "High intensity with core strength",
                        "Stay hydrated!"
                );

                workoutRepo.save(workout1);
                workoutRepo.save(workout2);
                workoutRepo.save(workout3);

                System.out.println("✅ Default workouts created:");
                System.out.println(" - " + workout1.getName());
                System.out.println(" - " + workout2.getName());
                System.out.println(" - " + workout3.getName());
            }
        }
    }

}