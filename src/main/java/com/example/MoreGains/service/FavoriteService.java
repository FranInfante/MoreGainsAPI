package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.FavoriteDTO;

import java.util.List;

public interface FavoriteService {
    List<FavoriteDTO> getAllFavorites();
    FavoriteDTO getFavoriteById(Integer id);
    FavoriteDTO saveFavorite(FavoriteDTO favoriteDTO);
    void deleteFavorite(Integer id);
}