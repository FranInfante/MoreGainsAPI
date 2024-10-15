package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.ExerciseDTO;
import com.example.MoreGains.model.dtos.SetDTO;
import com.example.MoreGains.model.dtos.WorkoutLogDTO;
import com.example.MoreGains.model.dtos.WorkoutLogExerciseDTO;
import com.example.MoreGains.model.entities.*;
import com.example.MoreGains.repository.ExerciseRepository;
import com.example.MoreGains.repository.UsersRepository;
import com.example.MoreGains.repository.WorkoutLogRepository;
import com.example.MoreGains.repository.WorkoutRepository;
import com.example.MoreGains.service.WorkoutLogService;
import com.example.MoreGains.util.mappers.WorkoutLogMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.MoreGains.util.mappers.ExerciseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutLogServiceImpl implements WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;
    private final UsersRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    public List<WorkoutLogDTO> getAllWorkoutLogs() {
        return workoutLogRepository.findAll()
                .stream()
                .map(WorkoutLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutLogDTO createWorkoutLog(WorkoutLogDTO workoutLogDTO) {
        // Fetch the user and workout from the repository
        Users user = userRepository.findById(workoutLogDTO.getUserId())
                .orElseThrow(() -> new RuntimeException(MessageConstants.USER_NOT_FOUND));

        Workout workout = workoutRepository.findById(workoutLogDTO.getWorkoutId())
                .orElseThrow(() -> new RuntimeException(MessageConstants.WORKOUT_NOT_FOUND));

        // Create the WorkoutLog entity
        WorkoutLog workoutLog = WorkoutLog.builder()
                .user(user)
                .workout(workout)
                .date(workoutLogDTO.getDate())
                .isEditing(workoutLogDTO.isEditing())
                .build();

        // Now iterate through exercises and their respective sets
        List<WorkoutLogExercise> exercises = workoutLogDTO.getExercises().stream()
                .flatMap(exerciseDTO -> exerciseDTO.getSets().stream().map(setDTO -> {
                    // Fetch the Exercise entity
                    Exercise exercise = exerciseRepository.findById(exerciseDTO.getExerciseId())
                            .orElseThrow(() -> new RuntimeException(MessageConstants.EXERCISE_NOT_FOUND));

                    // Create a WorkoutLogExercise for each set
                    return WorkoutLogExercise.builder()
                            .exercise(exercise)
                            .workoutLog(workoutLog)
                            .set(setDTO.getSet())  // Each set has set, reps, and weight
                            .reps(setDTO.getReps())
                            .weight(setDTO.getWeight())
                            .build();
                }))
                .collect(Collectors.toList());

        // Set exercises to the workout log
        workoutLog.setExercises(exercises);

        // Save the workout log and its exercises
        WorkoutLog savedWorkoutLog = workoutLogRepository.save(workoutLog);

        // Convert the saved entity back to DTO
        return WorkoutLogMapper.toDTO(savedWorkoutLog);
    }

    @Override
    public List<WorkoutLogDTO> getWorkoutLogsForUser(Integer userId) {
        return workoutLogRepository.findByUserId(userId)
                .stream()
                .map(WorkoutLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutLogDTO getWorkoutLogById(Integer id) {
        WorkoutLog workoutLog = workoutLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageConstants.WORKOUT_LOG_NOT_FOUND));
        return WorkoutLogMapper.toDTO(workoutLog);
    }

    @Override
    public void deleteWorkoutLog(Integer id) {
        workoutLogRepository.deleteById(id);
    }

    @Override
    public WorkoutLogDTO getWorkoutLogByUserIdAndIsEditing(Integer userId, Boolean isEditing) {
        WorkoutLog workoutLog = workoutLogRepository.findFirstByUserIdAndIsEditing(userId, isEditing)
                .orElseThrow(() -> new RuntimeException(MessageConstants.WORKOUT_LOG_NOT_FOUND));
        return WorkoutLogMapper.toDTO(workoutLog);
    }

    @Override
    public WorkoutLogDTO updateWorkoutLog(Integer id, WorkoutLogDTO workoutLogDTO) {
        WorkoutLog workoutLog = workoutLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.WORKOUT_LOG_NOT_FOUND));

        workoutLog.setDate(workoutLogDTO.getDate());
        workoutLog.setEditing(workoutLogDTO.isEditing());

        // Update exercises
        List<WorkoutLogExercise> existingExercises = workoutLog.getExercises();
        List<WorkoutLogExerciseDTO> updatedExercisesDTO = workoutLogDTO.getExercises();

        // Create a map of existing exercises for easy lookup by exerciseId
        Map<Integer, List<WorkoutLogExercise>> existingExerciseMap = existingExercises.stream()
                .collect(Collectors.groupingBy(e -> e.getExercise().getId()));

        // Update or create new sets
        List<WorkoutLogExercise> updatedExercises = updatedExercisesDTO.stream()
                .flatMap(exerciseDTO -> exerciseDTO.getSets().stream().map(setDTO -> {
                    // Fetch existing sets for this exercise from the map
                    List<WorkoutLogExercise> existingSets = existingExerciseMap.getOrDefault(exerciseDTO.getExerciseId(), new ArrayList<>());

                    // Check if a matching set already exists
                    WorkoutLogExercise existingSet = existingSets.stream()
                            .filter(set -> set.getSet().equals(setDTO.getSet()))
                            .findFirst()
                            .orElse(null);

                    if (existingSet != null) {
                        // Update the existing set
                        existingSet.setReps(setDTO.getReps());
                        existingSet.setWeight(setDTO.getWeight());
                        return existingSet;
                    } else {
                        // Create a new set if no existing one matches
                        return WorkoutLogExercise.builder()
                                .workoutLog(workoutLog)
                                .exercise(exerciseRepository.findById(exerciseDTO.getExerciseId())
                                        .orElseThrow(() -> new EntityNotFoundException(MessageConstants.EXERCISE_NOT_FOUND)))
                                .set(setDTO.getSet())
                                .reps(setDTO.getReps())
                                .weight(setDTO.getWeight())
                                .build();
                    }
                }))
                .collect(Collectors.toList());

        // Remove any exercises that are no longer present
        List<WorkoutLogExercise> exercisesToRemove = existingExercises.stream()
                .filter(e -> !updatedExercises.contains(e))
                .collect(Collectors.toList());

        exercisesToRemove.forEach(workoutLog::removeExercise);

        workoutLog.setExercises(updatedExercises);

        WorkoutLog savedWorkoutLog = workoutLogRepository.save(workoutLog);
        return WorkoutLogMapper.toDTO(savedWorkoutLog);
    }




    @Override
    public ExerciseDTO getExerciseById(Integer exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.EXERCISE_NOT_FOUND));
        return ExerciseMapper.exerciseEntityToDTO(exercise);
    }
}
