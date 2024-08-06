package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.Users;
import com.example.MoreGains.model.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    Workout findByNameIgnoreCase(String day);

    List<Workout> findAllByUser(Users u);
}
