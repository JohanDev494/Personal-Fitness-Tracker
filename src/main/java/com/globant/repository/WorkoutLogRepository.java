package com.globant.repository;

import com.globant.model.WorkoutLog;
import com.globant.model.User;

import java.util.List;
import java.util.Optional;

public interface WorkoutLogRepository {
    void save(WorkoutLog log);
    List<WorkoutLog> findAll();
    List<WorkoutLog> findByUser(User user);
    Optional<WorkoutLog> findById(Long id);
    void delete(Long id);
}
