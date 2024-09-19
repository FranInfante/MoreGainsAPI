package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.ExerciseDTO;
import com.example.MoreGains.model.entities.Exercise;
import com.example.MoreGains.model.entities.Workout;
import com.example.MoreGains.model.entities.WorkoutExercise;
import com.example.MoreGains.repository.ExerciseRepository;
import com.example.MoreGains.repository.WorkoutExerciseRepository;
import com.example.MoreGains.repository.WorkoutRepository;
import com.example.MoreGains.service.ExerciseService;
import com.example.MoreGains.util.ExerciseMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ch.qos.logback.core.util.StringUtil.capitalizeFirstLetter;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;

    @Override
    public List<ExerciseDTO> getAllExercises(Integer userId) {
        List<Exercise> exercises = exerciseRepository.findByUserIdIsNullOrUserId(userId);
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
        exercise.setIsAvailable(false);
        exerciseRepository.save(exercise);
    }

    @Override
    public ExerciseDTO checkAndCreateExercise(ExerciseDTO exerciseDTO, Integer planId, Integer workoutId) {
        exerciseDTO.setName(capitalizeFirstLetter(exerciseDTO.getName()));

        Optional<Exercise> existingExercise = exerciseRepository.findByNameIgnoreCase(exerciseDTO.getName());

        Exercise exercise;
        if (existingExercise.isPresent()) {
            exercise = existingExercise.get();
            // Return the DTO with the exists flag set to true
            ExerciseDTO exerciseDTOResponse = ExerciseMapper.exerciseEntityToDTO(exercise);
            exerciseDTOResponse.setExists(true);
            return exerciseDTOResponse;
        } else {
            exercise = ExerciseMapper.exerciseDTOToEntity(exerciseDTO);
            if (exerciseDTO.getDescription() == null) {
                exercise.setDescription(null);
            }
            if (exerciseDTO.getVideoUrl() == null) {
                exercise.setVideoUrl(null);
            }

            // Save the new exercise to the repository
            exercise = exerciseRepository.save(exercise);
            // Return the DTO with the exists flag set to false (newly created exercise)
            ExerciseDTO exerciseDTOResponse = ExerciseMapper.exerciseEntityToDTO(exercise);
            exerciseDTOResponse.setExists(false);
            return exerciseDTOResponse;
        }
    }
}