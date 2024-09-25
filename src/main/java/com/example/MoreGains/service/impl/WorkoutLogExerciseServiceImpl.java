package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.WorkoutLogExerciseDTO;
import com.example.MoreGains.model.entities.Exercise;
import com.example.MoreGains.model.entities.WorkoutLog;
import com.example.MoreGains.model.entities.WorkoutLogExercise;
import com.example.MoreGains.repository.ExerciseRepository;
import com.example.MoreGains.repository.WorkoutLogExerciseRepository;
import com.example.MoreGains.repository.WorkoutLogRepository;
import com.example.MoreGains.repository.WorkoutRepository;
import com.example.MoreGains.service.WorkoutLogExerciseService;
import com.example.MoreGains.util.mappers.WorkoutLogExerciseMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutLogExerciseServiceImpl implements WorkoutLogExerciseService {

    private final WorkoutLogExerciseRepository workoutLogExerciseRepository;
    private final WorkoutLogRepository workoutLogRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    public List<WorkoutLogExerciseDTO> findByWorkoutLogId(Integer workoutLogId) {
        return workoutLogExerciseRepository.findByWorkoutLogId(workoutLogId)
                .stream()
                .map(WorkoutLogExerciseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutLogExerciseDTO addWorkoutLogExercise(WorkoutLogExerciseDTO workoutLogExerciseDTO) {
        Exercise exercise = exerciseRepository.findById(workoutLogExerciseDTO.getExerciseId())
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        WorkoutLog workoutLog = workoutLogRepository.findById(workoutLogExerciseDTO.getWorkoutLogId())
                .orElseThrow(() -> new RuntimeException("Workout log not found"));


        WorkoutLogExercise workoutLogExercise = WorkoutLogExercise.builder()
                .exercise(exercise)
                .workoutLog(workoutLog)
                .sets(workoutLogExerciseDTO.getSets())
                .reps(workoutLogExerciseDTO.getReps())
                .weight(workoutLogExerciseDTO.getWeight())
                .notes(workoutLogExerciseDTO.getNotes())
                .build();

        WorkoutLogExercise savedWorkoutLogExercise = workoutLogExerciseRepository.save(workoutLogExercise);

        return WorkoutLogExerciseMapper.toDTO(savedWorkoutLogExercise);
    }

    @Override
    public WorkoutLogExerciseDTO updateWorkoutLogExercise(Integer id, WorkoutLogExerciseDTO workoutLogExerciseDTO) {
        WorkoutLogExercise workoutLogExercise = workoutLogExerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(MessageConstants.WORKOUT_LOG_EXERCISE_NOT_FOUND));

        workoutLogExercise.setSets(workoutLogExerciseDTO.getSets());
        workoutLogExercise.setReps(workoutLogExerciseDTO.getReps());
        workoutLogExercise.setWeight(workoutLogExerciseDTO.getWeight());
        workoutLogExercise.setNotes(workoutLogExerciseDTO.getNotes());

        WorkoutLogExercise updatedExercise = workoutLogExerciseRepository.save(workoutLogExercise);
        return WorkoutLogExerciseMapper.toDTO(updatedExercise);
    }

    @Override
    public void deleteWorkoutLogExercise(Integer id) {
        workoutLogExerciseRepository.deleteById(id);
    }
}
