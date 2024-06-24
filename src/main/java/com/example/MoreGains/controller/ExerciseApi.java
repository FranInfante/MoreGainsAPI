package com.example.MoreGains.controller;

import com.example.MoreGains.model.dtos.ExerciseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.MoreGains.util.UriConstants;

import java.util.List;

@RequestMapping(UriConstants.EXERCISES)
public interface ExerciseApi {

    @GetMapping
    ResponseEntity<List<ExerciseDTO>> getAllExercises();

    @GetMapping(UriConstants.BY_ID)
    ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<ExerciseDTO> createExercise(@RequestBody ExerciseDTO newExercise) throws Exception;

    @DeleteMapping(UriConstants.BY_ID)
    ResponseEntity<Void> deleteExerciseById(@PathVariable Integer id);

    @PutMapping(UriConstants.BY_ID)
    ResponseEntity<Void> updateExercise(@PathVariable Integer id, @RequestBody ExerciseDTO updateExercise) throws Exception;
}