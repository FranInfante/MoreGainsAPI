package com.example.MoreGains.util;

import com.example.MoreGains.model.dtos.UsersDTO;
import com.example.MoreGains.model.entities.Users;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UsersMapper {

    public Users userDTOToEntity(UsersDTO usersDTO) {
        return Users.builder()
                .id(usersDTO.getId())
                .username(usersDTO.getUsername())
                .password(usersDTO.getPassword())
                .email(usersDTO.getEmail())
                .photoUrl(usersDTO.getPhotoUrl())
                .bio(usersDTO.getBio())
                .fitnessGoals(usersDTO.getFitnessGoals())
                .isTrainer(usersDTO.getIsTrainer())
                .privacySetting(usersDTO.getPrivacySetting())
                .build();
    }

    public UsersDTO userEntityToDTO(Users users) {
        return UsersDTO.builder()
                .id(users.getId())
                .username(users.getUsername())
                .password(users.getPassword())
                .email(users.getEmail())
                .photoUrl(users.getPhotoUrl())
                .bio(users.getBio())
                .fitnessGoals(users.getFitnessGoals())
                .isTrainer(users.getIsTrainer())
                .privacySetting(users.getPrivacySetting())
                .build();
    }

    public List<Users> listUserDTOToEntity(List<UsersDTO> listUsersDto) {
        return listUsersDto.stream().map(UsersMapper::userDTOToEntity).collect(Collectors.toList());
    }

    public List<UsersDTO> listUserEntityToDTO(List<Users> listUsers) {
        return listUsers.stream().map(UsersMapper::userEntityToDTO).collect(Collectors.toList());
    }
}