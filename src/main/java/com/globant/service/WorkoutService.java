package com.globant.service;

import com.globant.model.Workout;

import java.util.List;
import java.util.Optional;

public interface WorkoutService {
    void createWorkout(Workout workout);
    List<Workout> getAllWorkouts();
    Optional<Workout> findByName(String name);
}
