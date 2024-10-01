package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.WorkoutLogDTO;
import com.example.MoreGains.model.entities.*;
import com.example.MoreGains.repository.ExerciseRepository;
import com.example.MoreGains.repository.UsersRepository;
import com.example.MoreGains.repository.WorkoutLogRepository;
import com.example.MoreGains.repository.WorkoutRepository;
import com.example.MoreGains.service.WorkoutLogService;
import com.example.MoreGains.util.mappers.WorkoutLogMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        Workout workout = workoutRepository.findById(workoutLogDTO.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        // Create the WorkoutLog entity
        WorkoutLog workoutLog = WorkoutLog.builder()
                .user(user)
                .workout(workout)
                .date(workoutLogDTO.getDate())
                .notes(workoutLogDTO.getNotes())
                .isEditing(workoutLogDTO.isEditing())
                .build();

        // Now iterate through exercises and their respective sets
        List<WorkoutLogExercise> exercises = workoutLogDTO.getExercises().stream()
                .flatMap(exerciseDTO -> exerciseDTO.getSets().stream().map(setDTO -> {
                    // Fetch the Exercise entity
                    Exercise exercise = exerciseRepository.findById(exerciseDTO.getExerciseId())
                            .orElseThrow(() -> new RuntimeException("Exercise not found"));

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
                .orElseThrow(() -> new RuntimeException("No workout log found with userId " + userId + " and isEditing = " + isEditing));
        return WorkoutLogMapper.toDTO(workoutLog);
    }

    @Override
    public WorkoutLogDTO updateWorkoutLog(Integer id, WorkoutLogDTO workoutLogDTO) {
        WorkoutLog workoutLog = workoutLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout log not found with id " + id));

        workoutLog.setNotes(workoutLogDTO.getNotes());
        workoutLog.setDate(workoutLogDTO.getDate());
        workoutLog.setEditing(workoutLogDTO.isEditing());

        workoutLog.getExercises().clear();
        List<WorkoutLogExercise> exercises = workoutLogDTO.getExercises().stream()
                .flatMap(exerciseDTO -> exerciseDTO.getSets().stream().map(setDTO -> {
                    Exercise exercise = exerciseRepository.findById(exerciseDTO.getExerciseId())
                            .orElseThrow(() -> new RuntimeException("Exercise not found"));

                    return WorkoutLogExercise.builder()
                            .exercise(exercise)
                            .workoutLog(workoutLog)
                            .set(setDTO.getSet())
                            .reps(setDTO.getReps())
                            .weight(setDTO.getWeight())
                            .build();
                }))
                .collect(Collectors.toList());

        workoutLog.setExercises(exercises);

        WorkoutLog updatedWorkoutLog = workoutLogRepository.save(workoutLog);

        return WorkoutLogMapper.toDTO(updatedWorkoutLog);
    }

}
