package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.MuscleGroupDTO;
import com.example.MoreGains.model.entities.MuscleGroup;
import lombok.experimental.UtilityClass;

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
}