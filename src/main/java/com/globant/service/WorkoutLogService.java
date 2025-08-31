package com.globant.service;

import com.globant.model.User;
import com.globant.model.WorkoutLog;

import java.util.List;

public interface WorkoutLogService {
    void save(WorkoutLog workoutLog);
    void logWorkout(WorkoutLog workoutLog);
    List<WorkoutLog> getAllLogs();
    List<WorkoutLog> getLogsByUser(User user);
}
