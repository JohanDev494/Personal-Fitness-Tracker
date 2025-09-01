package com.globant.service.impl;

import com.globant.model.Exercise;
import com.globant.repository.ExerciseRepository;
import com.globant.service.ExerciseService;

import java.util.List;
import java.util.Optional;

public record ExerciseServiceImpl(ExerciseRepository exerciseRepository) implements ExerciseService {

    @Override
    public void createExercise(Exercise exercise) {
        if (exerciseRepository.findByName(exercise.getName()).isPresent()) {
            throw new IllegalArgumentException("Exercise already exists.");
        }
        exerciseRepository.save(exercise);
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.getAll();
    }

    @Override
    public boolean updateExercise(String oldName, Exercise updatedExercise) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findByName(oldName);
        if (exerciseOpt.isPresent()) {
            Exercise existing = exerciseOpt.get();
            existing.setName(updatedExercise.getName());
            exerciseRepository.saveAll(exerciseRepository.getAll());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExercise(String name) {
        return exerciseRepository.deleteByName(name);
    }
}
