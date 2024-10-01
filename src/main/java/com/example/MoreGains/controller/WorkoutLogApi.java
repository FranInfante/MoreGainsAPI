package com.example.MoreGains.controller;

import com.example.MoreGains.model.dtos.WorkoutLogDTO;
import com.example.MoreGains.model.dtos.WorkoutLogSearchRequest;
import com.example.MoreGains.util.UriConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UriConstants.WORKOUT_LOG)
public interface WorkoutLogApi {

    @GetMapping
    ResponseEntity<List<WorkoutLogDTO>> getAllWorkoutLogs();

    @GetMapping(UriConstants.BY_USER_ID)
    ResponseEntity<List<WorkoutLogDTO>> getWorkoutLogsByUserId(@PathVariable Integer userId);

    @GetMapping(UriConstants.BY_ID)
    ResponseEntity<WorkoutLogDTO> getWorkoutLogById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<WorkoutLogDTO> createWorkoutLog(@RequestBody WorkoutLogDTO workoutLogDTO);

    @DeleteMapping(UriConstants.BY_ID)
    ResponseEntity<Void> deleteWorkoutLog(@PathVariable Integer id);

    @PostMapping(UriConstants.FIND_BY_USER_AND_ISEDITING)
    ResponseEntity<WorkoutLogDTO> getWorkoutLogByUserIdAndIsEditing(@RequestBody WorkoutLogSearchRequest request);

    @PutMapping(UriConstants.BY_ID)
    ResponseEntity<WorkoutLogDTO> updateWorkoutLog(@PathVariable Integer id, @RequestBody WorkoutLogDTO workoutLogDTO);
}
