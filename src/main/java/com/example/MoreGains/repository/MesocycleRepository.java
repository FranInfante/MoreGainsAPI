package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.Mesocycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesocycleRepository extends JpaRepository<Mesocycle, Integer> {
}
