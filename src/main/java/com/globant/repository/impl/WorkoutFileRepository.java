package com.globant.repository.impl;

import com.globant.model.Workout;
import com.globant.repository.WorkoutRepository;
import com.globant.storage.Storage;

import java.util.List;
import java.util.Optional;

public class WorkoutFileRepository implements WorkoutRepository {
    private final Storage<Workout> storage;
    private List<Workout> workouts;

    public WorkoutFileRepository(Storage<Workout> storage) {
        this.storage = storage;
        this.workouts = storage.load();
    }

    @Override
    public void save(Workout workout) {
        workouts.add(workout);
        storage.save(workouts);
    }

    @Override
    public List<Workout> getAll() {
        return List.copyOf(workouts);
    }

    @Override
    public Optional<Workout> findByName(String name) {
        return workouts.stream()
                .filter(workout -> workout.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
