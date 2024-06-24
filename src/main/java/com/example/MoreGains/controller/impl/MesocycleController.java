package com.example.MoreGains.controller.impl;

import com.example.MoreGains.controller.MesocycleApi;
import com.example.MoreGains.model.dtos.MesocycleDTO;
import com.example.MoreGains.service.MesocycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MesocycleController implements MesocycleApi {

    private final MesocycleService mesocycleService;

    @Override
    public ResponseEntity<List<MesocycleDTO>> getAllMesocycles() {
        List<MesocycleDTO> mesocycles = mesocycleService.getAllMesocycles();
        return ResponseEntity.ok(mesocycles);
    }

    @Override
    public ResponseEntity<MesocycleDTO> getMesocycleById(Integer id) {
        MesocycleDTO mesocycle = mesocycleService.getMesocycleById(id);
        return ResponseEntity.ok(mesocycle);
    }

    @Override
    public ResponseEntity<MesocycleDTO> createMesocycle(MesocycleDTO newMesocycle) throws Exception {
        MesocycleDTO createdMesocycle = mesocycleService.saveMesocycle(newMesocycle);
        return ResponseEntity.ok(createdMesocycle);
    }

    @Override
    public ResponseEntity<Void> deleteMesocycleById(Integer id) {
        mesocycleService.deleteMesocycle(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateMesocycle(Integer id, MesocycleDTO updateMesocycle) throws Exception {
        mesocycleService.saveMesocycle(updateMesocycle);
        return ResponseEntity.noContent().build();
    }
}