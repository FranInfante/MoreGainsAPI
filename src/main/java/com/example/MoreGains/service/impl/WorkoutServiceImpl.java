package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.model.entities.Workout;
import com.example.MoreGains.repository.WorkoutRepository;
import com.example.MoreGains.service.WorkoutService;
import com.example.MoreGains.util.WorkoutMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Override
    public List<WorkoutDTO> getAllWorkouts() {
        List<Workout> workouts = workoutRepository.findAll();
        return WorkoutMapper.listWorkoutEntityToDTO(workouts);
    }

    @Override
    public WorkoutDTO getWorkoutById(Integer id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_NOT_FOUND));
        return WorkoutMapper.workoutEntityToDTO(workout);
    }

    @Override
    public WorkoutDTO saveWorkout(WorkoutDTO workoutDTO) {
        Workout workout = WorkoutMapper.workoutDTOToEntity(workoutDTO);
        Workout savedWorkout = workoutRepository.save(workout);
        return WorkoutMapper.workoutEntityToDTO(savedWorkout);
    }

    @Override
    public void deleteWorkout(Integer id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_NOT_FOUND));
        workout.setIsAvailable(false);
        workoutRepository.save(workout);
    }
}