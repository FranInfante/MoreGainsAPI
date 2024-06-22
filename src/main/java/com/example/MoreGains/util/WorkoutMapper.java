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
                .users(UsersMapper.userDTOToEntity(workoutDTO.getUsers()))
                .date(workoutDTO.getDate())
                .name(workoutDTO.getName())
                .description(workoutDTO.getDescription())
                .build();
    }

    public WorkoutDTO workoutEntityToDTO(Workout workout) {
        return WorkoutDTO.builder()
                .id(workout.getId())
                .users(UsersMapper.userEntityToDTO(workout.getUsers()))
                .date(workout.getDate())
                .name(workout.getName())
                .description(workout.getDescription())
                .build();
    }

    public List<Workout> listWorkoutDTOToEntity(List<WorkoutDTO> listWorkoutDTO) {
        return listWorkoutDTO.stream().map(WorkoutMapper::workoutDTOToEntity).collect(Collectors.toList());
    }

    public List<WorkoutDTO> listWorkoutEntityToDTO(List<Workout> listWorkout) {
        return listWorkout.stream().map(WorkoutMapper::workoutEntityToDTO).collect(Collectors.toList());
    }
}