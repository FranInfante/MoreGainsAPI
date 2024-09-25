package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.WorkoutExerciseDTO;
import com.example.MoreGains.model.entities.WorkoutExercise;
import com.example.MoreGains.repository.WorkoutExerciseRepository;
import com.example.MoreGains.service.WorkoutExerciseService;
import com.example.MoreGains.util.mappers.WorkoutExerciseMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;

    @Override
    public List<WorkoutExerciseDTO> getAllWorkoutExercises() {
        List<WorkoutExercise> workoutExercises = workoutExerciseRepository.findAll();
        return WorkoutExerciseMapper.listWorkoutExerciseEntityToDTO(workoutExercises);
    }

    @Override
    public List<WorkoutExerciseDTO> getExercisesForWorkout(Integer workoutId) {
        List<WorkoutExercise> exercises = workoutExerciseRepository.findByWorkoutId(workoutId);
        return WorkoutExerciseMapper.listWorkoutExerciseEntityToDTO(exercises);
    }

    @Override
    public WorkoutExerciseDTO saveWorkoutExercise(WorkoutExerciseDTO workoutExerciseDTO) {
        WorkoutExercise workoutExercise = WorkoutExerciseMapper.workoutExerciseDTOToEntity(workoutExerciseDTO);
        WorkoutExercise savedWorkoutExercise = workoutExerciseRepository.save(workoutExercise);
        return WorkoutExerciseMapper.workoutExerciseEntityToDTO(savedWorkoutExercise);
    }

    @Override
    public void deleteWorkoutExercise(Integer id) {
        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_EXERCISE_NOT_FOUND));
        workoutExerciseRepository.delete(workoutExercise);
    }
}