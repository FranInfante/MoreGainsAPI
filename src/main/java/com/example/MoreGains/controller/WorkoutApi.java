package com.example.MoreGains.controller;

import com.example.MoreGains.model.dtos.UpdateWorkoutNameDTO;
import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.util.UriConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(UriConstants.WORKOUTS)
public interface WorkoutApi {

    @GetMapping
    ResponseEntity<List<WorkoutDTO>> getAllWorkouts();

    @GetMapping(UriConstants.BY_ID)
    ResponseEntity<WorkoutDTO> getWorkoutById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<WorkoutDTO> createWorkout(@RequestBody WorkoutDTO newWorkout) throws Exception;

    @DeleteMapping(UriConstants.BY_ID)
    ResponseEntity<Void> deleteWorkoutById(@PathVariable Integer id);

    @PutMapping(UriConstants.BY_ID)
    ResponseEntity<Void> updateWorkout(@PathVariable Integer id, @RequestBody WorkoutDTO updateWorkout) throws Exception;

    @PutMapping(UriConstants.UPDATE_NAME)
    ResponseEntity<WorkoutDTO> updateWorkoutName(@PathVariable Integer id, @RequestBody UpdateWorkoutNameDTO updateWorkoutNameDTO);

}
