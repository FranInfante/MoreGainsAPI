package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.ClientTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientTrainerRepository extends JpaRepository<ClientTrainer, Integer> {
    List<ClientTrainer> findByTrainerId(Integer trainerId);

    List<ClientTrainer> findByClientId(Integer clientId);
}
