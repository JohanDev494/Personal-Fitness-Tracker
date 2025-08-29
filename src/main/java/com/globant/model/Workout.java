package com.globant.model;

import java.util.List;

public class Workout {
    private String name;
    private List<Exercise> exercises;
    private String description;
    private String note;

    public Workout(String name, List<Exercise> exercises, String description, String note) {
        this.name = name;
        this.exercises = exercises;
        this.description = description;
        this.note = note;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
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
