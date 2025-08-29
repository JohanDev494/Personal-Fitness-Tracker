package com.globant.repository.impl;

import com.globant.model.WorkoutLog;
import com.globant.model.User;
import com.globant.repository.WorkoutLogRepository;
import com.globant.storage.Storage;
import com.globant.storage.impl.FileStorage;

import java.util.*;
import java.util.stream.Collectors;

public class WorkoutLogFileRepository implements WorkoutLogRepository {
    private final Storage<WorkoutLog> storage;
    private final Map<Long, WorkoutLog> cache = new HashMap<>();
    private long idCounter = 1L;

    public WorkoutLogFileRepository(String filename) {
        this.storage = new FileStorage<>(filename);
        loadCache();
    }

    @Override
    public void save(WorkoutLog log) {
        if (log.getId() == null) {
            log.setId(idCounter++);
        }
        cache.put(log.getId(), log);
        persist();
    }

    @Override
    public List<WorkoutLog> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public List<WorkoutLog> findByUser(User user) {
        return cache.values().stream()
                .filter(log -> log.getUser().equals(user))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WorkoutLog> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public void delete(Long id) {
        cache.remove(id);
        persist();
    }

    private void persist() {
        storage.save(new ArrayList<>(cache.values()));
    }

    private void loadCache() {
        List<WorkoutLog> logs = storage.load();
        logs.forEach(log -> {
            cache.put(log.getId(), log);
            if (log.getId() >= idCounter) {
                idCounter = log.getId() + 1;
            }
        });
    }
}
