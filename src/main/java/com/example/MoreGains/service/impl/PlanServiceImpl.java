package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.PlanDTO;
import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.model.entities.Plan;
import com.example.MoreGains.model.entities.Users;
import com.example.MoreGains.model.entities.Workout;
import com.example.MoreGains.repository.PlanRepository;
import com.example.MoreGains.repository.UsersRepository;
import com.example.MoreGains.service.PlanService;
import com.example.MoreGains.util.PlanMapper;
import com.example.MoreGains.util.WorkoutMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UsersRepository usersRepository;

    @Override
    public List<PlanDTO> getAllPlans() {
        List<Plan> plans = planRepository.findAll();
        return PlanMapper.listPlanEntityToDTO(plans);
    }

    @Override
    public PlanDTO getPlanById(Integer id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));
        return PlanMapper.planEntityToDTO(plan);
    }

    @Override
    public PlanDTO savePlan(PlanDTO planDTO) {
        Users user = usersRepository.findById(planDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Plan plan = PlanMapper.planDTOToEntity(planDTO);
        plan.setUser(user);

        // Set the user for each workout in the plan if workouts are provided
        if (plan.getWorkouts() != null) {
            plan.getWorkouts().forEach(workout -> workout.setUser(user));
        }

        Plan savedPlan = planRepository.save(plan);
        return PlanMapper.planEntityToDTO(savedPlan);
    }

    @Override
    public List<PlanDTO> getPlansByUserId(Integer userId) {
        List<Plan> plans = planRepository.findByUserId(userId);
        return PlanMapper.listPlanEntityToDTO(plans);
    }

    @Override
    public Optional<PlanDTO> updatePlan(Integer id, PlanDTO updatePlan) throws Exception {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        if (updatePlan.getName() != null) {
            plan.setName(updatePlan.getName());
        }

        // Update workouts and ensure the user is set
        plan.setWorkouts(updatePlan.getWorkouts().stream().map(workoutDTO -> {
            Workout workout = WorkoutMapper.workoutDTOToEntity(workoutDTO);
            workout.setUser(plan.getUser());
            return workout;
        }).collect(Collectors.toList()));

        Plan savedPlan = planRepository.save(plan);
        return Optional.of(PlanMapper.planEntityToDTO(savedPlan));
    }

    @Override
    public void deletePlan(Integer planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));
        planRepository.delete(plan);
    }

    @Override
    public PlanDTO addWorkoutToPlan(Integer planId, WorkoutDTO workoutDTO) throws Exception {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));

        Users user = plan.getUser();

        Workout workout = WorkoutMapper.workoutDTOToEntity(workoutDTO);
        workout.setUser(user);

        if (plan.getWorkouts() == null) {
            plan.setWorkouts(new ArrayList<>());
        }

        plan.getWorkouts().add(workout);

        Plan savedPlan = planRepository.save(plan);
        return PlanMapper.planEntityToDTO(savedPlan);
    }
}
