package com.example.MoreGains.util.mappers;

import com.example.MoreGains.model.dtos.WorkoutLogExerciseDTO;
import com.example.MoreGains.model.entities.Exercise;
import com.example.MoreGains.model.entities.WorkoutLog;
import com.example.MoreGains.model.entities.WorkoutLogExercise;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WorkoutLogExerciseMapper {

    public static WorkoutLogExerciseDTO toDTO(WorkoutLogExercise exercise) {
        WorkoutLogExerciseDTO dto = new WorkoutLogExerciseDTO();
        dto.setId(exercise.getId());
        dto.setExerciseId(exercise.getExercise().getId());
        dto.setSets(exercise.getSets());
        dto.setReps(exercise.getReps());
        dto.setWeight(exercise.getWeight());
        dto.setNotes(exercise.getNotes());
        dto.setWorkoutLogId(exercise.getWorkoutLog().getId());
        return dto;
    }

    public static WorkoutLogExercise toEntity(WorkoutLogExerciseDTO dto, WorkoutLog workoutLogEntity, Exercise exerciseEntity) {
        WorkoutLogExercise exercise = new WorkoutLogExercise();
        exercise.setSets(dto.getSets());
        exercise.setReps(dto.getReps());
        exercise.setWeight(dto.getWeight());
        exercise.setNotes(dto.getNotes());
        exercise.setWorkoutLog(workoutLogEntity);
        exercise.setExercise(exerciseEntity);

        return exercise;
    }
}
