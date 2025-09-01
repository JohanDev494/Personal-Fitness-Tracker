package com.globant.repository;

import com.globant.model.Workout;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository {
    void save(Workout workout);
    void saveAll(List<Workout> workouts);
    List<Workout> getAll();
    Optional<Workout> findByName(String name);
}


