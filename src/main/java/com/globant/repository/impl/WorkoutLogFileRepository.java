package com.globant.repository.impl;

import com.globant.model.User;
import com.globant.model.WorkoutLog;
import com.globant.repository.WorkoutLogRepository;
import com.globant.storage.Storage;

import java.util.List;
import java.util.stream.Collectors;

public class WorkoutLogFileRepository implements WorkoutLogRepository {

    private final Storage<WorkoutLog> storage;
    private final List<WorkoutLog> logs;

    public WorkoutLogFileRepository(Storage<WorkoutLog> storage) {
        this.storage = storage;
        this.logs = storage.load();
    }

    @Override
    public void save(WorkoutLog log) {
        logs.add(log);
        storage.save(logs);
    }

    @Override
    public List<WorkoutLog> getAllLogs() {
        return List.copyOf(logs);
    }

    @Override
    public List<WorkoutLog> getLogsByUser(User user) {
        return logs.stream()
                .filter(l -> l.getUser().getEmail().equals(user.getEmail()))
                .collect(Collectors.toList());
    }
}
