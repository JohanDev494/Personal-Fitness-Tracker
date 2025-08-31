package com.globant.service.impl;

import com.globant.model.Exercise;
import com.globant.repository.ExerciseRepository;
import com.globant.service.ExerciseService;

import java.util.List;
import java.util.Optional;

public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

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
    public Optional<Exercise> findByName(String name) {
        return exerciseRepository.findByName(name);
    }
}
