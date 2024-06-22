package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.ClientTrainerDTO;
import com.example.MoreGains.model.entities.ClientTrainer;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ClientTrainerMapper {

    public ClientTrainer clientTrainerDTOToEntity(ClientTrainerDTO clientTrainerDTO) {
        return ClientTrainer.builder()
                .id(clientTrainerDTO.getId())
                .trainer(UsersMapper.userDTOToEntity(clientTrainerDTO.getTrainer()))
                .client(UsersMapper.userDTOToEntity(clientTrainerDTO.getClient()))
                .build();
    }

    public ClientTrainerDTO clientTrainerEntityToDTO(ClientTrainer clientTrainer) {
        return ClientTrainerDTO.builder()
                .id(clientTrainer.getId())
                .trainer(UsersMapper.userEntityToDTO(clientTrainer.getTrainer()))
                .client(UsersMapper.userEntityToDTO(clientTrainer.getClient()))
                .build();
    }

    public List<ClientTrainer> listClientTrainerDTOToEntity(List<ClientTrainerDTO> listClientTrainerDTO) {
        return listClientTrainerDTO.stream().map(ClientTrainerMapper::clientTrainerDTOToEntity).collect(Collectors.toList());
    }

    public List<ClientTrainerDTO> listClientTrainerEntityToDTO(List<ClientTrainer> listClientTrainer) {
        return listClientTrainer.stream().map(ClientTrainerMapper::clientTrainerEntityToDTO).collect(Collectors.toList());
    }
}