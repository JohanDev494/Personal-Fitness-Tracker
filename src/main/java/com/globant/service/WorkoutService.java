package com.globant.service;

import com.globant.model.Workout;

import java.util.List;

public interface WorkoutService {
    void createWorkout(Workout workout);
    List<Workout> getAllWorkouts();
}
