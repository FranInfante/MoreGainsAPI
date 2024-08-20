package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Optional<Exercise> findByNameIgnoreCase(String name);

}