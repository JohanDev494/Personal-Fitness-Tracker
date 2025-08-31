package com.globant.model;

import java.util.List;

public class Workout {
    private String name;
    private List<WorkoutExercise> workoutExercise;
    private String description;
    private String note;

    public Workout(String name, List<WorkoutExercise> workoutExercise, String description, String note) {
        this.name = name;
        this.workoutExercise = workoutExercise;
        this.description = description;
        this.note = note;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<WorkoutExercise> getWorkoutExercise() {
        return workoutExercise;
    }
    public void setWorkoutExercise(List<WorkoutExercise> workoutExercise) {
        this.workoutExercise = workoutExercise;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
