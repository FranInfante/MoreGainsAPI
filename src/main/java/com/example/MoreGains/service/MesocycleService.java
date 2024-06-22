package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.MesocycleDTO;

import java.util.List;

public interface MesocycleService {
    List<MesocycleDTO> getAllMesocycles();
    MesocycleDTO getMesocycleById(Integer id);
    MesocycleDTO saveMesocycle(MesocycleDTO mesocycleDTO);
    void deleteMesocycle(Integer id);
}