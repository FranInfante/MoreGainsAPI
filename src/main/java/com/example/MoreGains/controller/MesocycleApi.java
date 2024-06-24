package com.example.MoreGains.controller;

import com.example.MoreGains.model.dtos.MesocycleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.MoreGains.util.UriConstants;

import java.util.List;

@RequestMapping(UriConstants.MESOCYCLES)
public interface MesocycleApi {

    @GetMapping
    ResponseEntity<List<MesocycleDTO>> getAllMesocycles();

    @GetMapping(UriConstants.MESOCYCLE_BY_ID)
    ResponseEntity<MesocycleDTO> getMesocycleById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<MesocycleDTO> createMesocycle(@RequestBody MesocycleDTO newMesocycle) throws Exception;

    @DeleteMapping(UriConstants.MESOCYCLE_BY_ID)
    ResponseEntity<Void> deleteMesocycleById(@PathVariable Integer id);

    @PutMapping(UriConstants.MESOCYCLE_BY_ID)
    ResponseEntity<Void> updateMesocycle(@PathVariable Integer id, @RequestBody MesocycleDTO updateMesocycle) throws Exception;
}