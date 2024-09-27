package com.example.MoreGains.util.mappers;

import com.example.MoreGains.model.dtos.WorkoutDTO;
import com.example.MoreGains.model.entities.Workout;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.MoreGains.util.messages.MessageConstants.NULL_WORKOUT_NAME;

@UtilityClass
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
                .sort(workout.getSort())
                .build();
    }

    public static Workout workoutDTOToEntity(WorkoutDTO workoutDTO) {
        if (workoutDTO == null) {
            return null;
        }

        Workout workout = new Workout();
        if (workoutDTO.getId() != null) {
            workout.setId(workoutDTO.getId());
        }
        workout.setDate(workoutDTO.getDate());

        if (workoutDTO.getName() == null || workoutDTO.getName().isEmpty()) {
            throw new IllegalArgumentException(NULL_WORKOUT_NAME);
        }
        workout.setName(workoutDTO.getName()); // Ensure this line is correctly setting the name

        if (workoutDTO.getDescription() != null) {
            workout.setDescription(workoutDTO.getDescription());
        }
        if (workoutDTO.getIsAvailable() != null) {
            workout.setIsAvailable(workoutDTO.getIsAvailable());
        }
        if (workoutDTO.getWorkoutExercises() != null) {
            workout.setWorkoutExercises(WorkoutExerciseMapper.listWorkoutExerciseDTOToEntity(workoutDTO.getWorkoutExercises()));
        }if (workoutDTO.getSort() != null) {
            workout.setSort(workoutDTO.getSort());
        } else {
            workout.setWorkoutExercises(new ArrayList<>()); // Default to empty list if not provided
        }
        return workout;
    }

    public static List<WorkoutDTO> listWorkoutEntityToDTO(List<Workout> workouts) {
        return workouts.stream().map(WorkoutMapper::workoutEntityToDTO).collect(Collectors.toList());
    }

    public static List<Workout> listWorkoutDTOToEntity(List<WorkoutDTO> workoutDTOs) {
        return workoutDTOs.stream().map(WorkoutMapper::workoutDTOToEntity).collect(Collectors.toList());
    }
}
