package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.ExerciseDTO;
import com.example.MoreGains.model.entities.Exercise;
import com.example.MoreGains.repository.ExerciseRepository;
import com.example.MoreGains.repository.WorkoutExerciseRepository;
import com.example.MoreGains.repository.WorkoutRepository;
import com.example.MoreGains.service.ExerciseService;
import com.example.MoreGains.util.mappers.ExerciseMapper;
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

        Optional<Exercise> existingExercise = exerciseRepository.findByNameIgnoreCaseAndUserId(
                exerciseDTO.getName(), exerciseDTO.getUserId());

        if (existingExercise.isPresent()) {
            Exercise foundExercise = existingExercise.get();

            // Check if the userId in the found exercise is different from the userId in the DTO
            if (foundExercise.getUserId() == null || !foundExercise.getUserId().equals(exerciseDTO.getUserId())) {
                return createNewExercise(exerciseDTO); // Create new exercise if userId doesn't match
            } else {
                return prepareExistingExerciseDTO(foundExercise); // Return the existing exercise
            }
        } else {
            return createNewExercise(exerciseDTO); // No exercise found, create a new one
        }
    }

    // Helper method to create a new exercise
    private ExerciseDTO createNewExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = ExerciseMapper.exerciseDTOToEntity(exerciseDTO);
        exercise.setUserId(exerciseDTO.getUserId());

        if (exerciseDTO.getDescription() == null) {
            exercise.setDescription(null);
        }
        if (exerciseDTO.getVideoUrl() == null) {
            exercise.setVideoUrl(null);
        }

        exercise = exerciseRepository.save(exercise);
        ExerciseDTO newExerciseDTO = ExerciseMapper.exerciseEntityToDTO(exercise);
        newExerciseDTO.setExists(false); // Indicating it's a new exercise

        return newExerciseDTO;
    }

    // Helper method to prepare the response for an existing exercise
    private ExerciseDTO prepareExistingExerciseDTO(Exercise foundExercise) {
        ExerciseDTO exerciseDTOResponse = ExerciseMapper.exerciseEntityToDTO(foundExercise);
        exerciseDTOResponse.setExists(true); // Indicating it's an existing exercise

        return exerciseDTOResponse;
    }

}