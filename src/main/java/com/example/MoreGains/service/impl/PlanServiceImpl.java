package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.PlanDTO;
import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.model.dtos.WorkoutExerciseDTO;
import com.example.MoreGains.model.entities.*;
import com.example.MoreGains.repository.*;
import com.example.MoreGains.service.PlanService;
import com.example.MoreGains.util.PlanMapper;
import com.example.MoreGains.util.WorkoutExerciseMapper;
import com.example.MoreGains.util.WorkoutMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UsersRepository usersRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    public List<PlanDTO> getAllPlans() {
        List<Plan> plans = planRepository.findAll();
        return PlanMapper.listPlanEntityToDTO(plans);
    }

    @Override
    public PlanDTO getPlanById(Integer id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));
        return PlanMapper.planEntityToDTO(plan);
    }

    @Override
    public PlanDTO savePlan(PlanDTO planDTO) {
        Users user = usersRepository.findById(planDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));

        Plan plan = PlanMapper.planDTOToEntity(planDTO);
        plan.setUser(user);

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
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));

        if (updatePlan.getName() != null) {
            plan.setName(updatePlan.getName());
        }

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
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));
        planRepository.delete(plan);
    }

    @Override
    public WorkoutDTO addWorkoutToPlan(Integer planId, WorkoutDTO workoutDTO) throws Exception {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));

        Users user = plan.getUser();

        Workout workout = WorkoutMapper.workoutDTOToEntity(workoutDTO);
        workout.setUser(user);

        if (workout.getDate() == null) {
            workout.setDate(LocalDate.now());
        }

        if (workout.getDescription() == null) {
            workout.setDescription(null);
        }

        if (workout.getWorkoutExercises() == null) {
            workout.setWorkoutExercises(null);
        }

        if (plan.getWorkouts() == null) {
            plan.setWorkouts(new ArrayList<>());
        }

        plan.getWorkouts().add(workout);

        workoutRepository.save(workout); // Save the new workout separately if needed

        Plan savedPlan = planRepository.save(plan); // Optionally save the plan, though not required to return the workout

        return WorkoutMapper.workoutEntityToDTO(workout); // Return the new workout
    }

    @Override
    public void deleteWorkoutExercise(Integer planId, Integer workoutId, Integer exerciseId) throws Exception {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_NOT_FOUND));

        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_EXERCISE_NOT_FOUND));

        workout.getWorkoutExercises().remove(workoutExercise);
        workoutExerciseRepository.delete(workoutExercise);
    }

    @Override
    public WorkoutExerciseDTO getWorkoutExercise(Integer planId, Integer workoutId, Integer exerciseId) throws Exception {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_NOT_FOUND));

        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_EXERCISE_NOT_FOUND));

        return WorkoutExerciseMapper.workoutExerciseEntityToDTO(workoutExercise);
    }

    @Override
    public WorkoutDTO addExerciseToWorkout(Integer planId, Integer workoutId, WorkoutExerciseDTO exerciseDTO) throws Exception {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.PLAN_NOT_FOUND));

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_NOT_FOUND));


        Exercise exercise = exerciseRepository.findByNameIgnoreCase(exerciseDTO.getExerciseName())
                .orElseGet(() -> {
                    Exercise newExercise = new Exercise();
                    newExercise.setName(exerciseDTO.getExerciseName());
                    newExercise.setIsAvailable(true);
                    return exerciseRepository.save(newExercise);
                });


        WorkoutExercise workoutExercise = WorkoutExerciseMapper.workoutExerciseDTOToEntity(exerciseDTO);
        workoutExercise.setExercise(exercise);
        workoutExercise.setWorkout(workout);


        workout.getWorkoutExercises().add(workoutExercise);

        workoutExerciseRepository.save(workoutExercise);
        workoutRepository.save(workout);

        return WorkoutMapper.workoutEntityToDTO(workout);
    }

}