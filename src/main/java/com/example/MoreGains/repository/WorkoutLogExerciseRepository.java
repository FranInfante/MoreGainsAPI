package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.WorkoutLogExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutLogExerciseRepository extends JpaRepository<WorkoutLogExercise, Integer> {
    List<WorkoutLogExercise> findByWorkoutLogId(Integer workoutLogId);
}
