package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.ClientTrainerDTO;

import java.util.List;

public interface ClientTrainerService {
    List<ClientTrainerDTO> getAllClientTrainers();
    List<ClientTrainerDTO> getAllTrainersForClient(Integer clientId);
    List<ClientTrainerDTO> getAllClientsForTrainer(Integer trainerId);
    ClientTrainerDTO saveClientTrainer(ClientTrainerDTO clientTrainerDTO);
    void deleteClientTrainer(Integer id);
}