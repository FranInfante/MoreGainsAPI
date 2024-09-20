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

        Optional<Exercise> existingExercise = exerciseRepository.findByNameIgnoreCaseAndUserId(
                exerciseDTO.getName(), exerciseDTO.getUserId());

        Exercise exercise;
        if (existingExercise.isPresent()) {
            Exercise foundExercise = existingExercise.get();

            // Check if the userId in the found exercise is different from the userId in the DTO
            if (foundExercise.getUserId() == null || !foundExercise.getUserId().equals(exerciseDTO.getUserId())) {
                // Create a new exercise with the userId from the DTO
                exercise = ExerciseMapper.exerciseDTOToEntity(exerciseDTO);
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
            } else {
                // Return the existing exercise if the userId matches
                ExerciseDTO exerciseDTOResponse = ExerciseMapper.exerciseEntityToDTO(foundExercise);
                exerciseDTOResponse.setExists(true); // Indicating it's an existing exercise

                return exerciseDTOResponse;
            }
        } else {
            // No exercise found, create a new one
            exercise = ExerciseMapper.exerciseDTOToEntity(exerciseDTO);
            exercise.setUserId(exerciseDTO.getUserId());

            if (exerciseDTO.getDescription() == null) {
                exercise.setDescription(null);
            }
            if (exerciseDTO.getVideoUrl() == null) {
                exercise.setVideoUrl(null);
            }

            // Save the new exercise to the repository
            exercise = exerciseRepository.save(exercise);
            ExerciseDTO newExerciseDTO = ExerciseMapper.exerciseEntityToDTO(exercise);
            newExerciseDTO.setExists(false); // Indicating it's a newly created exercise

            return newExerciseDTO;
        }
    }

}