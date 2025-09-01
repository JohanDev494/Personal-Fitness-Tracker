package com.globant.service.impl;

import com.globant.model.Workout;
import com.globant.repository.WorkoutRepository;
import com.globant.service.WorkoutService;

import java.util.List;

public record WorkoutServiceImpl(WorkoutRepository workoutRepository) implements WorkoutService {

    @Override
    public void createWorkout(Workout workout) {
        workoutRepository.save(workout);
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.getAll();
    }
}
