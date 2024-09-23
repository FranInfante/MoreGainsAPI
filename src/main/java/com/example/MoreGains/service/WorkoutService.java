package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.WorkoutDTO;

import java.util.List;

public interface WorkoutService {
    List<WorkoutDTO> getAllWorkouts();
    WorkoutDTO getWorkoutById(Integer id);
    WorkoutDTO saveWorkout(WorkoutDTO workoutDTO);
    void deleteWorkout(Integer id);
    WorkoutDTO updateWorkoutName(Integer id, String name) throws Exception;
}