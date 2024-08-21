package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.MuscleGroup;
import com.example.MoreGains.model.enums.MuscleGroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Integer> {
    MuscleGroup findByName(MuscleGroupType name);
}