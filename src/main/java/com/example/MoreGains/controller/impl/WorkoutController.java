package com.example.MoreGains.controller.impl;

import com.example.MoreGains.controller.WorkoutApi;
import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WorkoutController implements WorkoutApi {

    private final WorkoutService workoutService;

    @Override
    public ResponseEntity<List<WorkoutDTO>> getAllWorkouts() {
        List<WorkoutDTO> workouts = workoutService.getAllWorkouts();
        return ResponseEntity.ok(workouts);
    }

    @Override
    public ResponseEntity<WorkoutDTO> getWorkoutById(Integer id) {
        WorkoutDTO workout = workoutService.getWorkoutById(id);
        return ResponseEntity.ok(workout);
    }

    @Override
    public ResponseEntity<WorkoutDTO> createWorkout(WorkoutDTO newWorkout) throws Exception {
        WorkoutDTO createdWorkout = workoutService.saveWorkout(newWorkout);
        return ResponseEntity.ok(createdWorkout);
    }

    @Override
    public ResponseEntity<Void> deleteWorkoutById(Integer id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateWorkout(Integer id, WorkoutDTO updateWorkout) throws Exception {
        workoutService.saveWorkout(updateWorkout);
        return ResponseEntity.noContent().build();
    }
}
