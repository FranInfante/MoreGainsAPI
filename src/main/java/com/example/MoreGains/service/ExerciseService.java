package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.ExerciseDTO;

import java.util.List;

public interface ExerciseService {
    List<ExerciseDTO> getAllExercises();
    ExerciseDTO getExerciseById(Integer id);
    ExerciseDTO saveExercise(ExerciseDTO exerciseDTO);
    void deleteExercise(Integer id);
    ExerciseDTO checkAndCreateExercise(ExerciseDTO exerciseDTO);
}
