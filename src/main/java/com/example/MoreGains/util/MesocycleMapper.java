package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.MesocycleDTO;
import com.example.MoreGains.model.entities.Mesocycle;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MesocycleMapper {

    public Mesocycle mesocycleDTOToEntity(MesocycleDTO mesocycleDTO) {
        return Mesocycle.builder()
                .id(mesocycleDTO.getId())
                .users(UsersMapper.userDTOToEntity(mesocycleDTO.getUsers()))
                .name(mesocycleDTO.getName())
                .description(mesocycleDTO.getDescription())
                .startDate(mesocycleDTO.getStartDate())
                .endDate(mesocycleDTO.getEndDate())
                .isAvailable(mesocycleDTO.getIsAvailable())
                .build();
    }

    public MesocycleDTO mesocycleEntityToDTO(Mesocycle mesocycle) {
        return MesocycleDTO.builder()
                .id(mesocycle.getId())
                .users(UsersMapper.userEntityToDTO(mesocycle.getUsers()))
                .name(mesocycle.getName())
                .description(mesocycle.getDescription())
                .startDate(mesocycle.getStartDate())
                .endDate(mesocycle.getEndDate())
                .isAvailable(mesocycle.getIsAvailable())
                .build();
    }

    public List<Mesocycle> listMesocycleDTOToEntity(List<MesocycleDTO> listMesocycleDTO) {
        return listMesocycleDTO.stream().map(MesocycleMapper::mesocycleDTOToEntity).collect(Collectors.toList());
    }

    public List<MesocycleDTO> listMesocycleEntityToDTO(List<Mesocycle> listMesocycle) {
        return listMesocycle.stream().map(MesocycleMapper::mesocycleEntityToDTO).collect(Collectors.toList());
    }
}