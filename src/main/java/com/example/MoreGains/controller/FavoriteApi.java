package com.example.MoreGains.controller;

import com.example.MoreGains.model.dtos.FavoriteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.MoreGains.util.UriConstants;

import java.util.List;

@RequestMapping(UriConstants.FAVORITES)
public interface FavoriteApi {

    @GetMapping
    ResponseEntity<List<FavoriteDTO>> getAllFavorites();

    @GetMapping(UriConstants.BY_ID)
    ResponseEntity<FavoriteDTO> getFavoriteById(@PathVariable Integer id);

    @PostMapping
    ResponseEntity<FavoriteDTO> createFavorite(@RequestBody FavoriteDTO newFavorite) throws Exception;

    @DeleteMapping(UriConstants.BY_ID)
    ResponseEntity<Void> deleteFavoriteById(@PathVariable Integer id);

    @PutMapping(UriConstants.BY_ID)
    ResponseEntity<Void> updateFavorite(@PathVariable Integer id, @RequestBody FavoriteDTO updateFavorite) throws Exception;
}