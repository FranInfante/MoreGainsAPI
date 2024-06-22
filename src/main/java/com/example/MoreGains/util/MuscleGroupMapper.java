package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.MuscleGroupDTO;
import com.example.MoreGains.model.entities.MuscleGroup;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MuscleGroupMapper {

    public MuscleGroup muscleGroupDTOToEntity(MuscleGroupDTO muscleGroupDTO) {
        return MuscleGroup.builder()
                .id(muscleGroupDTO.getId())
                .name(muscleGroupDTO.getName())
                .build();
    }

    public MuscleGroupDTO muscleGroupEntityToDTO(MuscleGroup muscleGroup) {
        return MuscleGroupDTO.builder()
                .id(muscleGroup.getId())
                .name(muscleGroup.getName())
                .build();
    }
    public List<MuscleGroup> listMuscleGroupDTOToEntity(List<MuscleGroupDTO> listMuscleGroupDTO) {
        return listMuscleGroupDTO.stream().map(MuscleGroupMapper::muscleGroupDTOToEntity).collect(Collectors.toList());
    }

    public List<MuscleGroupDTO> listMuscleGroupEntityToDTO(List<MuscleGroup> listMuscleGroup) {
        return listMuscleGroup.stream().map(MuscleGroupMapper::muscleGroupEntityToDTO).collect(Collectors.toList());
    }
}