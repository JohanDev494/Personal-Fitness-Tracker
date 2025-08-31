package com.globant.service.impl;

import com.globant.model.Workout;
import com.globant.repository.WorkoutRepository;
import com.globant.service.WorkoutService;

import java.util.List;
import java.util.Optional;

public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public void createWorkout(Workout workout) {
        if (workoutRepository.findByName(workout.getName()).isPresent()) {
            throw new IllegalArgumentException("A workout with this name already exists.");
        }
        workoutRepository.save(workout);
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.getAll();
    }

    @Override
    public Optional<Workout> findByName(String name) {
        return workoutRepository.findByName(name);
    }
}
