package com.globant.repository.impl;

import com.globant.model.Exercise;
import com.globant.repository.ExerciseRepository;
import com.globant.storage.Storage;

import java.util.List;
import java.util.Optional;

public class ExerciseFileRepository implements ExerciseRepository {

    private final Storage<Exercise> storage;
    private final List<Exercise> exercises;

    public ExerciseFileRepository(Storage<Exercise> storage) {
        this.storage = storage;
        this.exercises = storage.load();
    }

    @Override
    public void save(Exercise exercise) {
        exercises.add(exercise);
        storage.save(exercises);
    }

    @Override
    public List<Exercise> getAll() {
        return List.copyOf(exercises);
    }

    @Override
    public Optional<Exercise> findByName(String name) {
        return exercises.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public void saveAll(List<Exercise> updatedExercises) {
        exercises.clear();
        exercises.addAll(updatedExercises);
        storage.save(exercises);
    }

    @Override
    public boolean deleteByName(String name) {
        Optional<Exercise> exerciseOpt = findByName(name);
        if (exerciseOpt.isPresent()) {
            exercises.remove(exerciseOpt.get());
            storage.save(exercises);
            return true;
        }
        return false;
    }

}
