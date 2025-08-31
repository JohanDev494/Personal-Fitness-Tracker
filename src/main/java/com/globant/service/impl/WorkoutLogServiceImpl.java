package com.globant.service.impl;

import com.globant.model.User;
import com.globant.model.WorkoutLog;
import com.globant.repository.WorkoutLogRepository;
import com.globant.service.WorkoutLogService;

import java.util.List;

public class WorkoutLogServiceImpl implements WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;

    public WorkoutLogServiceImpl(WorkoutLogRepository workoutLogRepository) {
        this.workoutLogRepository = workoutLogRepository;
    }

    @Override
    public void save(WorkoutLog workoutLog) {
        workoutLogRepository.save(workoutLog);
    }

    @Override
    public void logWorkout(WorkoutLog workoutLog) {
        workoutLogRepository.save(workoutLog);
    }

    @Override
    public List<WorkoutLog> getAllLogs() {
        return workoutLogRepository.getAllLogs();
    }

    @Override
    public List<WorkoutLog> getLogsByUser(User user) {
        return workoutLogRepository.getLogsByUser(user);
    }
}
