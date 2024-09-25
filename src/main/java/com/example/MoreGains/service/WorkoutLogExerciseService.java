package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.WorkoutLogExerciseDTO;

import java.util.List;

public interface WorkoutLogExerciseService {

    List<WorkoutLogExerciseDTO> findByWorkoutLogId(Integer workoutLogId);

    WorkoutLogExerciseDTO addWorkoutLogExercise(WorkoutLogExerciseDTO workoutLogExerciseDTO);

    WorkoutLogExerciseDTO updateWorkoutLogExercise(Integer id, WorkoutLogExerciseDTO workoutLogExerciseDTO);

    void deleteWorkoutLogExercise(Integer id);
}
