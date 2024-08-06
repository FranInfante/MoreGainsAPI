package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.PlanDTO;
import com.example.MoreGains.model.dtos.WorkoutDTO;
import org.springframework.web.multipart.MultipartFile;

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
}