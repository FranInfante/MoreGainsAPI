package com.example.MoreGains.controller.impl;

import com.example.MoreGains.controller.FavoriteApi;
import com.example.MoreGains.model.dtos.FavoriteDTO;
import com.example.MoreGains.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController implements FavoriteApi {

    private final FavoriteService favoriteService;

    @Override
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        List<FavoriteDTO> favorites = favoriteService.getAllFavorites();
        return ResponseEntity.ok(favorites);
    }

    @Override
    public ResponseEntity<FavoriteDTO> getFavoriteById(Integer id) {
        FavoriteDTO favorite = favoriteService.getFavoriteById(id);
        return ResponseEntity.ok(favorite);
    }

    @Override
    public ResponseEntity<FavoriteDTO> createFavorite(FavoriteDTO newFavorite) throws Exception {
        FavoriteDTO createdFavorite = favoriteService.saveFavorite(newFavorite);
        return ResponseEntity.ok(createdFavorite);
    }

    @Override
    public ResponseEntity<Void> deleteFavoriteById(Integer id) {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateFavorite(Integer id, FavoriteDTO updateFavorite) throws Exception {
        favoriteService.saveFavorite(updateFavorite);
        return ResponseEntity.noContent().build();
    }
}