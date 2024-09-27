package com.example.MoreGains.util.mappers;

import com.example.MoreGains.model.dtos.SetDTO;
import com.example.MoreGains.model.dtos.WorkoutLogDTO;
import com.example.MoreGains.model.dtos.WorkoutLogExerciseDTO;
import com.example.MoreGains.model.entities.Exercise;
import com.example.MoreGains.model.entities.WorkoutLog;
import com.example.MoreGains.model.entities.WorkoutLogExercise;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class WorkoutLogExerciseMapper {

    public static WorkoutLogExerciseDTO toDTO(WorkoutLogExercise workoutLogExercise) {
        WorkoutLogExerciseDTO dto = new WorkoutLogExerciseDTO();

        dto.setId(workoutLogExercise.getId());
        dto.setExerciseId(workoutLogExercise.getExercise().getId());
        dto.setWorkoutLogId(workoutLogExercise.getWorkoutLog().getId());

        // Group sets under this exercise
        SetDTO setDTO = new SetDTO(workoutLogExercise.getSet(), workoutLogExercise.getReps(), workoutLogExercise.getWeight());
        dto.setSets(List.of(setDTO));

        return dto;
    }

    public static WorkoutLogExercise toEntity(SetDTO setDTO, WorkoutLog workoutLog, Exercise exercise) {
        WorkoutLogExercise exerciseEntity = new WorkoutLogExercise();
        exerciseEntity.setSet(setDTO.getSet());
        exerciseEntity.setReps(setDTO.getReps());
        exerciseEntity.setWeight(setDTO.getWeight());
        exerciseEntity.setWorkoutLog(workoutLog);
        exerciseEntity.setExercise(exercise);

        return exerciseEntity;
    }
}
