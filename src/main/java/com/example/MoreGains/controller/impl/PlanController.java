package com.example.MoreGains.controller.impl;

import com.example.MoreGains.controller.PlanApi;
import com.example.MoreGains.model.dtos.PlanDTO;
import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.service.PlanService;
import com.example.MoreGains.util.UriConstants;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UriConstants.PLANS)
public class PlanController implements PlanApi {

    private final PlanService planService;

    @Override
    public ResponseEntity<PlanDTO> getPlanById(@PathVariable Integer id) {
        PlanDTO plan = planService.getPlanById(id);
        return ResponseEntity.ok(plan);
    }

    @Override
    public ResponseEntity<List<PlanDTO>> getAllPlans() {
        List<PlanDTO> plans = planService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @Override
    public ResponseEntity<List<PlanDTO>> getPlansByUserId(@PathVariable Integer userId) {
        List<PlanDTO> plans = planService.getPlansByUserId(userId);
        return ResponseEntity.ok(plans);
    }

    @Override
    public ResponseEntity<PlanDTO> createPlan(@RequestBody PlanDTO newPlan) throws Exception {
        PlanDTO createdPlan = planService.savePlan(newPlan);
        return ResponseEntity.ok(createdPlan);
    }

    @Override
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable Integer id, @RequestBody PlanDTO updatePlan) throws Exception {
        PlanDTO updatedPlan = planService.updatePlan(id, updatePlan).orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));
        return ResponseEntity.ok(updatedPlan);
    }

    @Override
    public ResponseEntity<Void> deletePlan(@PathVariable Integer id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PlanDTO> addWorkoutToPlan(@PathVariable Integer planId, @RequestBody WorkoutDTO workoutDTO) throws Exception {
        PlanDTO updatedPlan = planService.addWorkoutToPlan(planId, workoutDTO);
        return ResponseEntity.ok(updatedPlan);
    }
}
