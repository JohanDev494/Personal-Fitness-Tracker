package com.globant.repository;

import com.globant.model.WorkoutLog;
import com.globant.model.User;

import java.util.List;

public interface WorkoutLogRepository {
    void save(WorkoutLog log);
    List<WorkoutLog> getAllLogs();
    List<WorkoutLog> getLogsByUser(User user);
}
