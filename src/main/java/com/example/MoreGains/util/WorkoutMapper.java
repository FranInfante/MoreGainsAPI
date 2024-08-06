package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.model.entities.Workout;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class WorkoutMapper {

    public Workout workoutDTOToEntity(WorkoutDTO workoutDTO) {
        return Workout.builder()
                .id(workoutDTO.getId())
                .name(workoutDTO.getName())
                .description(workoutDTO.getDescription())
                .isAvailable(workoutDTO.getIsAvailable())
                .date(workoutDTO.getDate())
                .workoutExercises(workoutDTO.getWorkoutExercises().stream().map(WorkoutExerciseMapper::workoutExerciseDTOToEntity).collect(Collectors.toList()))
                .build();
    }

    public WorkoutDTO workoutEntityToDTO(Workout workout) {
        return WorkoutDTO.builder()
                .id(workout.getId())
                .name(workout.getName())
                .description(workout.getDescription())
                .isAvailable(workout.getIsAvailable())
                .date(workout.getDate())
                .workoutExercises(workout.getWorkoutExercises().stream().map(WorkoutExerciseMapper::workoutExerciseEntityToDTO).collect(Collectors.toList()))
                .build();
    }

    public List<Workout> listWorkoutDTOToEntity(List<WorkoutDTO> listWorkoutDTO) {
        return listWorkoutDTO.stream().map(WorkoutMapper::workoutDTOToEntity).collect(Collectors.toList());
    }

    public List<WorkoutDTO> listWorkoutEntityToDTO(List<Workout> listWorkout) {
        return listWorkout.stream().map(WorkoutMapper::workoutEntityToDTO).collect(Collectors.toList());
    }
}
