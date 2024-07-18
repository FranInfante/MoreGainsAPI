package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    ClassValue<Object> findByNameIgnoreCase(String day);
}
