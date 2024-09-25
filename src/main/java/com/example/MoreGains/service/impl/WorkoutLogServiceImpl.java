package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.WorkoutLogDTO;
import com.example.MoreGains.model.entities.Users;
import com.example.MoreGains.model.entities.Workout;
import com.example.MoreGains.model.entities.WorkoutLog;
import com.example.MoreGains.model.entities.WorkoutLogExercise;
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

        // Map DTO to Entity
        WorkoutLog workoutLog = WorkoutLog.builder()
                .user(user)
                .workout(workout)
                .date(workoutLogDTO.getDate())
                .notes(workoutLogDTO.getNotes())
                .build();

        List<WorkoutLogExercise> exercises = workoutLogDTO.getExercises()
                .stream()
                .map(exerciseDTO -> WorkoutLogExercise.builder()
                        .exercise(exerciseRepository.findById(exerciseDTO.getExerciseId())
                                .orElseThrow(() -> new RuntimeException("Exercise not found")))
                        .sets(exerciseDTO.getSets())
                        .reps(exerciseDTO.getReps())
                        .weight(exerciseDTO.getWeight())
                        .notes(exerciseDTO.getNotes())
                        .workoutLog(workoutLog) // Associate the exercise with the workout log
                        .build())
                .collect(Collectors.toList());

        // Set exercises in workout log
        workoutLog.setExercises(exercises);

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
}
