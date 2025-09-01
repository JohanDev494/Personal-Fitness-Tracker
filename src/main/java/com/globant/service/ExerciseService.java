package com.globant.service;

import com.globant.model.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    void createExercise(Exercise exercise);
    List<Exercise> getAllExercises();
    boolean updateExercise(String oldName, Exercise updatedExercise);
    boolean deleteExercise(String name);
}
