package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.WorkoutLogDTO;

import java.util.List;

public interface WorkoutLogService {

    List<WorkoutLogDTO> getAllWorkoutLogs();

    WorkoutLogDTO createWorkoutLog(WorkoutLogDTO workoutLogDTO);

    List<WorkoutLogDTO> getWorkoutLogsForUser(Integer userId);

    WorkoutLogDTO getWorkoutLogById(Integer id);

    void deleteWorkoutLog(Integer id);
}
