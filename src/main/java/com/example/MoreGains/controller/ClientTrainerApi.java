package com.example.MoreGains.controller;

import com.example.MoreGains.model.dtos.ClientTrainerDTO;
import com.example.MoreGains.util.UriConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(UriConstants.CLIENT_TRAINER)
public interface ClientTrainerApi {

    @GetMapping
    ResponseEntity<List<ClientTrainerDTO>> getAllClientTrainers();

    @GetMapping(UriConstants.BY_TRAINER_ID) // Differentiating path for trainer ID
    ResponseEntity<List<ClientTrainerDTO>> getAllClientsForTrainer(@PathVariable Integer trainerId);

    @GetMapping(UriConstants.BY_CLIENT_ID) // Differentiating path for client ID
    ResponseEntity<List<ClientTrainerDTO>> getAllTrainersForClient(@PathVariable Integer clientId);

    @PostMapping
    ResponseEntity<ClientTrainerDTO> saveClientTrainer(@RequestBody ClientTrainerDTO clientTrainerDTO) throws Exception;

    @DeleteMapping(UriConstants.BY_ID)
    ResponseEntity<Void> deleteClientTrainer(@PathVariable Integer id);

}