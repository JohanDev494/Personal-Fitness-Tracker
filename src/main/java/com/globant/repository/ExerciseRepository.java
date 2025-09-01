package com.globant.repository;

import com.globant.model.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository {
    void save(Exercise exercise);
    void saveAll(List<Exercise> updatedExercises);
    List<Exercise> getAll();
    Optional<Exercise> findByName(String name);
    boolean deleteByName(String name);
}
