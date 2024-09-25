package com.example.MoreGains.util.mappers;

import com.example.MoreGains.model.dtos.WorkoutLogDTO;
import com.example.MoreGains.model.entities.WorkoutLog;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.stream.Collectors;

@UtilityClass
public class WorkoutLogMapper {

    public static WorkoutLogDTO toDTO(WorkoutLog workoutLog) {
        WorkoutLogDTO dto = new WorkoutLogDTO();
        dto.setId(workoutLog.getId());
        dto.setUserId(workoutLog.getUser().getId());
        dto.setWorkoutId(workoutLog.getWorkout().getId());
        dto.setDate(workoutLog.getDate());
        dto.setNotes(workoutLog.getNotes());
        dto.setExercises(workoutLog.getExercises() != null
                ? workoutLog.getExercises().stream()
                .map(WorkoutLogExerciseMapper::toDTO)
                .collect(Collectors.toList())
                : new ArrayList<>());
        return dto;
    }

    public static WorkoutLog toEntity(WorkoutLogDTO dto) {
        WorkoutLog workoutLog = new WorkoutLog();
        workoutLog.setDate(dto.getDate());
        workoutLog.setNotes(dto.getNotes());
        return workoutLog;
    }
}
