package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.FavoriteDTO;
import com.example.MoreGains.model.entities.Favorite;
import com.example.MoreGains.repository.FavoriteRepository;
import com.example.MoreGains.service.FavoriteService;
import com.example.MoreGains.util.mappers.FavoriteMapper;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    public List<FavoriteDTO> getAllFavorites() {
        List<Favorite> favorites = favoriteRepository.findAll();
        return FavoriteMapper.listFavoriteEntityToDTO(favorites);
    }

    @Override
    public FavoriteDTO getFavoriteById(Integer id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.FAVORITE_NOT_FOUND));
        return FavoriteMapper.favoriteEntityToDTO(favorite);
    }

    @Override
    public FavoriteDTO saveFavorite(FavoriteDTO favoriteDTO) {
        Favorite favorite = FavoriteMapper.favoriteDTOToEntity(favoriteDTO);
        Favorite savedFavorite = favoriteRepository.save(favorite);
        return FavoriteMapper.favoriteEntityToDTO(savedFavorite);
    }

    @Override
    public void deleteFavorite(Integer id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.FAVORITE_NOT_FOUND));
        favorite.setIsAvailable(false);
        favoriteRepository.save(favorite);
    }
}