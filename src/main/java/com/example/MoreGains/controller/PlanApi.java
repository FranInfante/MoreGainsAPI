package com.example.MoreGains.controller;

import com.example.MoreGains.model.dtos.PlanDTO;
import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.util.UriConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UriConstants.PLANS)
public interface PlanApi {

    @GetMapping(UriConstants.BY_ID)
    ResponseEntity<PlanDTO> getPlanById(@PathVariable Integer id);

    @GetMapping
    ResponseEntity<List<PlanDTO>> getAllPlans();

    @GetMapping(UriConstants.PLANS_BY_USER_ID)
    ResponseEntity<List<PlanDTO>> getPlansByUserId(@PathVariable Integer userId);

    @PostMapping
    ResponseEntity<PlanDTO> createPlan(@RequestBody PlanDTO newPlan) throws Exception;

    @PutMapping(UriConstants.BY_ID)
    ResponseEntity<PlanDTO> updatePlan(@PathVariable Integer id, @RequestBody PlanDTO updatePlan) throws Exception;

    @DeleteMapping(UriConstants.BY_ID)
    ResponseEntity<Void> deletePlan(@PathVariable Integer id);

    @PostMapping("/{planId}/workouts")
    ResponseEntity<PlanDTO> addWorkoutToPlan(@PathVariable Integer planId, @RequestBody WorkoutDTO workoutDTO) throws Exception;
}
