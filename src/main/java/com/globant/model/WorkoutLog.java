package com.globant.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public class WorkoutLog implements Serializable {
    private Long id;
    private User user;
    private Workout workout;
    private LocalDateTime date;
    private Map<Exercise, Integer> timePerExercise;
    private int totalTime;

    public WorkoutLog(Long id, User user, LocalDateTime date, Workout workout, Map<Exercise, Integer> timePerExercise) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.workout = workout;
        this.timePerExercise = timePerExercise;
        this.totalTime = timePerExercise.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void logExerciseTime(Exercise exercise, int minutes) {
        timePerExercise.put(exercise, minutes);
        totalTime += minutes;
    }

    public void printSummary() {
        System.out.println("Workout Log for user: " + user.getName());
        System.out.println("Workout: " + workout.getName() + " on " + date);
        for (Map.Entry<Exercise, Integer> entry : timePerExercise.entrySet()) {
            System.out.println(" - " + entry.getKey().getName() + ": " + entry.getValue() + " min");
        }
        System.out.println("Total time: " + totalTime + " min");
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Workout getWorkout() { return workout; }
    public void setWorkout(Workout workout) { this.workout = workout; }

    public Map<Exercise, Integer> getTimePerExercise() { return timePerExercise; }
    public void setTimePerExercise(Map<Exercise, Integer> timePerExercise) { this.timePerExercise = timePerExercise; }

    public int getTotalTime() { return totalTime; }
    public void setTotalTime(int totalTime) { this.totalTime = totalTime; }
}
