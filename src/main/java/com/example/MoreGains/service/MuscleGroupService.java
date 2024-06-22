package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.MuscleGroupDTO;

import java.util.List;

public interface MuscleGroupService {
    List<MuscleGroupDTO> getAllMuscleGroups();
    MuscleGroupDTO getMuscleGroupById(Integer id);
    MuscleGroupDTO saveMuscleGroup(MuscleGroupDTO muscleGroupDTO);
    void deleteMuscleGroup(Integer id);
}