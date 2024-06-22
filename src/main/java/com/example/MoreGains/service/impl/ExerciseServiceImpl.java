package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.ExerciseDTO;
import com.example.MoreGains.model.entities.Exercise;
import com.example.MoreGains.repository.ExerciseRepository;
import com.example.MoreGains.service.ExerciseService;
import com.example.MoreGains.util.ExerciseMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public List<ExerciseDTO> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return ExerciseMapper.listExerciseEntityToDTO(exercises);
    }

    @Override
    public ExerciseDTO getExerciseById(Integer id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageConstants.EXERCISE_NOT_FOUND));
        return ExerciseMapper.exerciseEntityToDTO(exercise);
    }

    @Override
    public ExerciseDTO saveExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = ExerciseMapper.exerciseDTOToEntity(exerciseDTO);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return ExerciseMapper.exerciseEntityToDTO(savedExercise);
    }

    @Override
    public void deleteExercise(Integer id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageConstants.EXERCISE_NOT_FOUND));
        exerciseRepository.delete(exercise);
    }
}