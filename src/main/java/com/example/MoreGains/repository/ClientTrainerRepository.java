package com.example.MoreGains.repository;

import com.example.MoreGains.model.ClientTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTrainerRepository extends JpaRepository<ClientTrainer, Integer> {
}
