package com.globant.model;

import java.io.Serializable;
import java.util.Map;

public class WorkoutLog implements Serializable {
    private User user;
    private Workout workout;
    private String date;
    private Map<String, Integer> timePerExercise;
    private int totalTime;

    public WorkoutLog(User user, String date, Workout workout, Map<String, Integer> timePerExercise) {
        this.user = user;
        this.date = date;
        this.workout = workout;
        this.timePerExercise = timePerExercise;
        this.totalTime = timePerExercise.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void logExerciseTime(String exerciseName, int minutes) {
        timePerExercise.put(exerciseName, minutes);
        totalTime += minutes;
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Workout getWorkout() { return workout; }
    public void setWorkout(Workout workout) { this.workout = workout; }

    public Map<String, Integer> getTimePerExercise() { return timePerExercise; }
    public void setTimePerExercise(Map<String, Integer> timePerExercise) { this.timePerExercise = timePerExercise; }

    public int getTotalTime() { return totalTime; }
    public void setTotalTime(int totalTime) { this.totalTime = totalTime; }
}
