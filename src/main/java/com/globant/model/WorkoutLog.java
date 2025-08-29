package com.globant.model;

import java.time.LocalDateTime;
import java.util.Map;

public class WorkoutLog {
    private User user;
    private Workout workout;
    private LocalDateTime date;
    private Map<Exercise, Integer> timePerExercise;
    private int totalTime;

    public WorkoutLog(LocalDateTime date, Workout workout, Map<Exercise, Integer> timePerExercise, int totalTime) {
        this.date = date;
        this.workout = workout;
        this.timePerExercise = timePerExercise;
        this.totalTime = totalTime;
    }

    public void logExerciseTime(Exercise exercise, int minutes) {
        timePerExercise.put(exercise, minutes);
        totalTime += minutes;
    }

    public void printSummary(){
        System.out.println("Workout Log for: " + workout.getName() + " on " + date);
        for (Map.Entry<Exercise, Integer> entry : timePerExercise.entrySet()) {
            System.out.println(" - " + entry.getKey().getName() + ": " + entry.getValue() + " min");
        }
        System.out.println("Total time: " + totalTime + " min");
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Workout getWorkout() {
        return workout;
    }
    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Map<Exercise, Integer> getTimePerExercise() {
        return timePerExercise;
    }
    public void setTimePerExercise(Map<Exercise, Integer> timePerExercise) {
        this.timePerExercise = timePerExercise;
    }

    public int getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
