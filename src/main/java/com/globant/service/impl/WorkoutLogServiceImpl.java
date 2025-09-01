package com.globant.service.impl;

import com.globant.model.User;
import com.globant.model.WorkoutLog;
import com.globant.repository.WorkoutLogRepository;
import com.globant.service.WorkoutLogService;

import java.util.List;

public record WorkoutLogServiceImpl(WorkoutLogRepository workoutLogRepository) implements WorkoutLogService {

    @Override
    public void save(WorkoutLog workoutLog) {
        workoutLogRepository.save(workoutLog);
    }

    @Override
    public List<WorkoutLog> getLogsByUser(User user) {
        return workoutLogRepository.getLogsByUser(user);
    }
}
