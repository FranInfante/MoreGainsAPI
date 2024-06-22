package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.WorkoutExerciseDTO;
import com.example.MoreGains.model.entities.WorkoutExercise;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class WorkoutExerciseMapper {

    public WorkoutExercise workoutExerciseDTOToEntity(WorkoutExerciseDTO workoutExerciseDTO) {
        return WorkoutExercise.builder()
                .id(workoutExerciseDTO.getId())
                .workout(WorkoutMapper.workoutDTOToEntity(workoutExerciseDTO.getWorkout()))
                .exercise(ExerciseMapper.exerciseDTOToEntity(workoutExerciseDTO.getExercise()))
                .sets(workoutExerciseDTO.getSets())
                .reps(workoutExerciseDTO.getReps())
                .weight(workoutExerciseDTO.getWeight())
                .build();
    }

    public WorkoutExerciseDTO workoutExerciseEntityToDTO(WorkoutExercise workoutExercise) {
        return WorkoutExerciseDTO.builder()
                .id(workoutExercise.getId())
                .workout(WorkoutMapper.workoutEntityToDTO(workoutExercise.getWorkout()))
                .exercise(ExerciseMapper.exerciseEntityToDTO(workoutExercise.getExercise()))
                .sets(workoutExercise.getSets())
                .reps(workoutExercise.getReps())
                .weight(workoutExercise.getWeight())
                .build();
    }

    public List<WorkoutExercise> listWorkoutExerciseDTOToEntity(List<WorkoutExerciseDTO> listWorkoutExerciseDTO) {
        return listWorkoutExerciseDTO.stream().map(WorkoutExerciseMapper::workoutExerciseDTOToEntity).collect(Collectors.toList());
    }

    public List<WorkoutExerciseDTO> listWorkoutExerciseEntityToDTO(List<WorkoutExercise> listWorkoutExercise) {
        return listWorkoutExercise.stream().map(WorkoutExerciseMapper::workoutExerciseEntityToDTO).collect(Collectors.toList());
    }
}