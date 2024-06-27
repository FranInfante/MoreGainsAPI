package com.example.MoreGains.controller.impl;

import com.example.MoreGains.controller.ClientTrainerApi;
import com.example.MoreGains.model.dtos.ClientTrainerDTO;
import com.example.MoreGains.service.ClientTrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientTrainerController implements ClientTrainerApi {

    private final ClientTrainerService clientTrainerService;

    @Override
    public ResponseEntity<List<ClientTrainerDTO>> getAllClientTrainers() {
        List<ClientTrainerDTO> clientTrainer = clientTrainerService.getAllClientTrainers();
        return ResponseEntity.ok(clientTrainer);
    }

    @Override
    public ResponseEntity<List<ClientTrainerDTO>> getAllTrainersForClient(Integer clientId) {
        List<ClientTrainerDTO> clientTrainers = clientTrainerService.getAllTrainersForClient(clientId);
        return ResponseEntity.ok(clientTrainers);
    }

    @Override
    public ResponseEntity<List<ClientTrainerDTO>> getAllClientsForTrainer(Integer trainerId) {
        List<ClientTrainerDTO> trainerClients = clientTrainerService.getAllClientsForTrainer(trainerId);
        return ResponseEntity.ok(trainerClients);
    }

    @Override
    public ResponseEntity<ClientTrainerDTO> saveClientTrainer(ClientTrainerDTO clientTrainerDTO) throws Exception {
        ClientTrainerDTO createdClientTrainer = clientTrainerService.saveClientTrainer(clientTrainerDTO);
        return ResponseEntity.ok(createdClientTrainer);
    }

    @Override
    public ResponseEntity<Void> deleteClientTrainer(Integer id) {
        clientTrainerService.deleteClientTrainer(id);
        return ResponseEntity.noContent().build();
    }
}