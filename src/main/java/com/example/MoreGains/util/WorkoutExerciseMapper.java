package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.WorkoutExerciseDTO;
import com.example.MoreGains.model.entities.WorkoutExercise;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutExerciseMapper {

    public static WorkoutExerciseDTO workoutExerciseEntityToDTO(WorkoutExercise workoutExercise) {
        if (workoutExercise == null) {
            return null;
        }

        return WorkoutExerciseDTO.builder()
                .id(workoutExercise.getId())
                .reps(workoutExercise.getReps() != null ? workoutExercise.getReps() : 0)
                .sets(workoutExercise.getSets() != null ? workoutExercise.getSets() : 0)
                .weight(workoutExercise.getWeight() != null ? workoutExercise.getWeight() : 0)
                .exerciseName(workoutExercise.getExercise().getName())
                .build();
    }

    public static WorkoutExercise workoutExerciseDTOToEntity(WorkoutExerciseDTO workoutExerciseDTO) {
        if (workoutExerciseDTO == null) {
            return null;
        }

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setId(workoutExerciseDTO.getId());
        workoutExercise.setReps(workoutExerciseDTO.getReps() != null ? workoutExerciseDTO.getReps() : 0);
        workoutExercise.setSets(workoutExerciseDTO.getSets() != null ? workoutExerciseDTO.getSets() : 0);
        workoutExercise.setWeight(workoutExerciseDTO.getWeight() != null ? workoutExerciseDTO.getWeight() : 0);

        return workoutExercise;
    }

    public static List<WorkoutExerciseDTO> listWorkoutExerciseEntityToDTO(List<WorkoutExercise> workoutExercises) {
        return workoutExercises.stream().map(WorkoutExerciseMapper::workoutExerciseEntityToDTO).collect(Collectors.toList());
    }

    public static List<WorkoutExercise> listWorkoutExerciseDTOToEntity(List<WorkoutExerciseDTO> workoutExerciseDTOs) {
        return workoutExerciseDTOs.stream().map(WorkoutExerciseMapper::workoutExerciseDTOToEntity).collect(Collectors.toList());
    }
}
