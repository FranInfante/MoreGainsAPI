package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.PlanDTO;
import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.model.dtos.WorkoutExerciseDTO;
import com.example.MoreGains.model.entities.WorkoutExercise;

import java.util.List;
import java.util.Optional;

public interface PlanService {
    List<PlanDTO> getAllPlans();
    PlanDTO getPlanById(Integer id);
    PlanDTO savePlan(PlanDTO planDTO);
    void deletePlan(Integer id);
    List<PlanDTO> getPlansByUserId(Integer userId);
    Optional<PlanDTO> updatePlan(Integer id, PlanDTO updatePlan) throws Exception;
    PlanDTO addWorkoutToPlan(Integer planId, WorkoutDTO workoutDTO) throws Exception;
    void deleteWorkoutExercise(Integer planId, Integer workoutId, Integer exerciseId) throws Exception;
    WorkoutExerciseDTO getWorkoutExercise(Integer planId, Integer workoutId, Integer exerciseId) throws Exception;
}