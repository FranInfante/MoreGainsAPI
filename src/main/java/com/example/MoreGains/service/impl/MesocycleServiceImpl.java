package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.MesocycleDTO;
import com.example.MoreGains.model.entities.Mesocycle;
import com.example.MoreGains.repository.MesocycleRepository;
import com.example.MoreGains.service.MesocycleService;
import com.example.MoreGains.util.MesocycleMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesocycleServiceImpl implements MesocycleService {

    private final MesocycleRepository mesocycleRepository;

    @Override
    public List<MesocycleDTO> getAllMesocycles() {
        List<Mesocycle> mesocycles = mesocycleRepository.findAll();
        return MesocycleMapper.listMesocycleEntityToDTO(mesocycles);
    }

    @Override
    public MesocycleDTO getMesocycleById(Integer id) {
        Mesocycle mesocycle = mesocycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.MESOCYCLE_NOT_FOUND));
        return MesocycleMapper.mesocycleEntityToDTO(mesocycle);
    }

    @Override
    public MesocycleDTO saveMesocycle(MesocycleDTO mesocycleDTO) {
        Mesocycle mesocycle = MesocycleMapper.mesocycleDTOToEntity(mesocycleDTO);
        Mesocycle savedMesocycle = mesocycleRepository.save(mesocycle);
        return MesocycleMapper.mesocycleEntityToDTO(savedMesocycle);
    }

    @Override
    public void deleteMesocycle(Integer id) {
        Mesocycle mesocycle = mesocycleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.MESOCYCLE_NOT_FOUND));
        mesocycle.setIsAvailable(false);
        mesocycleRepository.save(mesocycle);
    }
}