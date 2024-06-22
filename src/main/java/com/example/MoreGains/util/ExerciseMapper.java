package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.ExerciseDTO;
import com.example.MoreGains.model.entities.Exercise;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ExerciseMapper {

    public Exercise exerciseDTOToEntity(ExerciseDTO exerciseDTO) {
        return Exercise.builder()
                .id(exerciseDTO.getId())
                .name(exerciseDTO.getName())
                .description(exerciseDTO.getDescription())
                .videoUrl(exerciseDTO.getVideoUrl())
                .muscleGroup(MuscleGroupMapper.muscleGroupDTOToEntity(exerciseDTO.getMuscleGroup()))
                .build();
    }

    public ExerciseDTO exerciseEntityToDTO(Exercise exercise) {
        return ExerciseDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .videoUrl(exercise.getVideoUrl())
                .muscleGroup(MuscleGroupMapper.muscleGroupEntityToDTO(exercise.getMuscleGroup()))
                .build();
    }

    public List<Exercise> listExerciseDTOToEntity(List<ExerciseDTO> listExerciseDTO) {
        return listExerciseDTO.stream().map(ExerciseMapper::exerciseDTOToEntity).collect(Collectors.toList());
    }

    public List<ExerciseDTO> listExerciseEntityToDTO(List<Exercise> listExercise) {
        return listExercise.stream().map(ExerciseMapper::exerciseEntityToDTO).collect(Collectors.toList());
    }
}