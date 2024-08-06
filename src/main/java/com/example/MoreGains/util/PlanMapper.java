package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.PlanDTO;
import com.example.MoreGains.model.entities.Plan;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PlanMapper {

    public Plan planDTOToEntity(PlanDTO planDTO) {
        return Plan.builder()
                .id(planDTO.getId())
                .name(planDTO.getName())
                .build();
    }

    public PlanDTO planEntityToDTO(Plan plan) {
        return PlanDTO.builder()
                .id(plan.getId())
                .name(plan.getName())
                .userId(plan.getUser().getId())
                .workouts(plan.getWorkouts().stream().map(WorkoutMapper::workoutEntityToDTO).collect(Collectors.toList()))
                .build();
    }

    public List<Plan> listPlanDTOToEntity(List<PlanDTO> listPlanDTO) {
        return listPlanDTO.stream().map(PlanMapper::planDTOToEntity).collect(Collectors.toList());
    }

    public List<PlanDTO> listPlanEntityToDTO(List<Plan> listPlan) {
        return listPlan.stream().map(PlanMapper::planEntityToDTO).collect(Collectors.toList());
    }
}
