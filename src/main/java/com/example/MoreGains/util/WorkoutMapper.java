package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.model.entities.Workout;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutMapper {

    public static WorkoutDTO workoutEntityToDTO(Workout workout) {
        if (workout == null) {
            return null;
        }

        return WorkoutDTO.builder()
                .id(workout.getId())
                .date(workout.getDate())
                .name(workout.getName())
                .description(workout.getDescription())
                .isAvailable(workout.getIsAvailable())
                .workoutExercises(WorkoutExerciseMapper.listWorkoutExerciseEntityToDTO(workout.getWorkoutExercises()))
                .build();
    }

    public static Workout workoutDTOToEntity(WorkoutDTO workoutDTO) {
        if (workoutDTO == null) {
            return null;
        }

        Workout workout = new Workout();
        workout.setId(workoutDTO.getId());
        workout.setDate(workoutDTO.getDate());
        workout.setName(workoutDTO.getName());
        workout.setDescription(workoutDTO.getDescription());
        workout.setIsAvailable(workoutDTO.getIsAvailable());
        workout.setWorkoutExercises(WorkoutExerciseMapper.listWorkoutExerciseDTOToEntity(workoutDTO.getWorkoutExercises()));

        return workout;
    }

    public static List<WorkoutDTO> listWorkoutEntityToDTO(List<Workout> workouts) {
        return workouts.stream().map(WorkoutMapper::workoutEntityToDTO).collect(Collectors.toList());
    }

    public static List<Workout> listWorkoutDTOToEntity(List<WorkoutDTO> workoutDTOs) {
        return workoutDTOs.stream().map(WorkoutMapper::workoutDTOToEntity).collect(Collectors.toList());
    }
}
