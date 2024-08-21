package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.Users;
import com.example.MoreGains.model.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    Optional<Workout> findByNameIgnoreCase(String day);

    List<Workout> findAllByUser(Users u);
}
