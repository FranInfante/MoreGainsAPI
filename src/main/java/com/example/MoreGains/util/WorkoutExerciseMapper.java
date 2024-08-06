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
                .reps(workoutExerciseDTO.getReps())
                .sets(workoutExerciseDTO.getSets())
                .weight(workoutExerciseDTO.getWeight())
                .workout(WorkoutMapper.workoutDTOToEntity(workoutExerciseDTO.getWorkout()))
                .exercise(ExerciseMapper.exerciseDTOToEntity(workoutExerciseDTO.getExercise()))
                .build();
    }

    public WorkoutExerciseDTO workoutExerciseEntityToDTO(WorkoutExercise workoutExercise) {
        return WorkoutExerciseDTO.builder()
                .id(workoutExercise.getId())
                .reps(workoutExercise.getReps())
                .sets(workoutExercise.getSets())
                .weight(workoutExercise.getWeight())
                .workout(WorkoutMapper.workoutEntityToDTO(workoutExercise.getWorkout()))
                .exercise(ExerciseMapper.exerciseEntityToDTO(workoutExercise.getExercise()))
                .build();
    }

    public List<WorkoutExercise> listWorkoutExerciseDTOToEntity(List<WorkoutExerciseDTO> listWorkoutExerciseDTO) {
        return listWorkoutExerciseDTO.stream().map(WorkoutExerciseMapper::workoutExerciseDTOToEntity).collect(Collectors.toList());
    }

    public List<WorkoutExerciseDTO> listWorkoutExerciseEntityToDTO(List<WorkoutExercise> listWorkoutExercise) {
        return listWorkoutExercise.stream().map(WorkoutExerciseMapper::workoutExerciseEntityToDTO).collect(Collectors.toList());
    }
}
