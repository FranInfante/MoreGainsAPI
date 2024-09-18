package com.example.MoreGains.controller.impl;

import com.example.MoreGains.controller.ExerciseApi;
import com.example.MoreGains.model.dtos.ExerciseDTO;
import com.example.MoreGains.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExerciseController implements ExerciseApi {

    private final ExerciseService exerciseService;

    @Override
    public ResponseEntity<List<ExerciseDTO>> getAllExercises(Integer userId) {
        List<ExerciseDTO> exercises = exerciseService.getAllExercises(userId);
        return ResponseEntity.ok(exercises);
    }

    @Override
    public ResponseEntity<ExerciseDTO> getExerciseById(Integer id) {
        ExerciseDTO exercise = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(exercise);
    }

    @Override
    public ResponseEntity<ExerciseDTO> createExercise(ExerciseDTO newExercise) throws Exception {
        ExerciseDTO createdExercise = exerciseService.saveExercise(newExercise);
        return ResponseEntity.ok(createdExercise);
    }

    @Override
    public ResponseEntity<Void> deleteExerciseById(Integer id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateExercise(Integer id, ExerciseDTO updateExercise) throws Exception {
        exerciseService.saveExercise(updateExercise);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ExerciseDTO> checkAndCreateExercise(ExerciseDTO exerciseDTO) {
        ExerciseDTO createdExercise = exerciseService.checkAndCreateExercise(exerciseDTO);
        return ResponseEntity.ok(createdExercise);
    }
}