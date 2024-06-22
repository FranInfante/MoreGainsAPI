package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.FavoriteDTO;
import com.example.MoreGains.model.entities.Favorite;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class FavoriteMapper {

    public Favorite favoriteDTOToEntity(FavoriteDTO favoriteDTO) {
        return Favorite.builder()
                .id(favoriteDTO.getId())
                .users(UsersMapper.userDTOToEntity(favoriteDTO.getUsers()))
                .exercise(ExerciseMapper.exerciseDTOToEntity(favoriteDTO.getExercise()))
                .build();
    }

    public FavoriteDTO favoriteEntityToDTO(Favorite favorite) {
        return FavoriteDTO.builder()
                .id(favorite.getId())
                .users(UsersMapper.userEntityToDTO(favorite.getUsers()))
                .exercise(ExerciseMapper.exerciseEntityToDTO(favorite.getExercise()))
                .build();
    }

    public List<Favorite> listFavoriteDTOToEntity(List<FavoriteDTO> listFavoriteDTO) {
        return listFavoriteDTO.stream().map(FavoriteMapper::favoriteDTOToEntity).collect(Collectors.toList());
    }

    public List<FavoriteDTO> listFavoriteEntityToDTO(List<Favorite> listFavorite) {
        return listFavorite.stream().map(FavoriteMapper::favoriteEntityToDTO).collect(Collectors.toList());
    }
}