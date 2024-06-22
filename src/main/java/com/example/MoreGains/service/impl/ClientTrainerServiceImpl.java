package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.ClientTrainerDTO;
import com.example.MoreGains.model.entities.ClientTrainer;
import com.example.MoreGains.repository.ClientTrainerRepository;
import com.example.MoreGains.service.ClientTrainerService;
import com.example.MoreGains.util.ClientTrainerMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientTrainerServiceImpl implements ClientTrainerService {

    private final ClientTrainerRepository clientTrainerRepository;

    @Override
    public List<ClientTrainerDTO> getAllClientTrainers() {
        List<ClientTrainer> clientTrainers = clientTrainerRepository.findAll();
        return ClientTrainerMapper.listClientTrainerEntityToDTO(clientTrainers);
    }

    @Override
    public List<ClientTrainerDTO> getAllTrainersForClient(Integer clientId) {
        List<ClientTrainer> trainers = clientTrainerRepository.findByClientId(clientId);
        return ClientTrainerMapper.listClientTrainerEntityToDTO(trainers);
    }

    @Override
    public List<ClientTrainerDTO> getAllClientsForTrainer(Integer trainerId) {
        List<ClientTrainer> clients = clientTrainerRepository.findByTrainerId(trainerId);
        return ClientTrainerMapper.listClientTrainerEntityToDTO(clients);
    }

    @Override
    public ClientTrainerDTO saveClientTrainer(ClientTrainerDTO clientTrainerDTO) {
        ClientTrainer clientTrainer = ClientTrainerMapper.clientTrainerDTOToEntity(clientTrainerDTO);
        ClientTrainer savedClientTrainer = clientTrainerRepository.save(clientTrainer);
        return ClientTrainerMapper.clientTrainerEntityToDTO(savedClientTrainer);
    }

    @Override
    public void deleteClientTrainer(Integer id) {
        ClientTrainer clientTrainer = clientTrainerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.CLIENT_TRAINER_NOT_FOUND));
        clientTrainerRepository.delete(clientTrainer);
    }
}