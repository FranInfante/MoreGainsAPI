package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.MuscleGroupDTO;
import com.example.MoreGains.model.entities.MuscleGroup;
import com.example.MoreGains.repository.MuscleGroupRepository;
import com.example.MoreGains.service.MuscleGroupService;
import com.example.MoreGains.util.MuscleGroupMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MuscleGroupServiceImpl implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;

    @Override
    public List<MuscleGroupDTO> getAllMuscleGroups() {
        List<MuscleGroup> muscleGroups = muscleGroupRepository.findAll();
        return MuscleGroupMapper.listMuscleGroupEntityToDTO(muscleGroups);
    }

    @Override
    public MuscleGroupDTO getMuscleGroupById(Integer id) {
        MuscleGroup muscleGroup = muscleGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.MUSCLEGROUP_NOT_FOUND));
        return MuscleGroupMapper.muscleGroupEntityToDTO(muscleGroup);
    }

    @Override
    public MuscleGroupDTO saveMuscleGroup(MuscleGroupDTO muscleGroupDTO) {
        MuscleGroup muscleGroup = MuscleGroupMapper.muscleGroupDTOToEntity(muscleGroupDTO);
        MuscleGroup savedMuscleGroup = muscleGroupRepository.save(muscleGroup);
        return MuscleGroupMapper.muscleGroupEntityToDTO(savedMuscleGroup);
    }

    @Override
    public void deleteMuscleGroup(Integer id) {
        MuscleGroup muscleGroup = muscleGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.MUSCLEGROUP_NOT_FOUND));
        muscleGroup.setIsAvailable(false);
        muscleGroupRepository.save(muscleGroup);
    }
}