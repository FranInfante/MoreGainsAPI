package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.UsersDTO;

import java.util.List;

public interface UsersService {
    List<UsersDTO> getAllUsers();
    UsersDTO getUserById(Integer id);
    UsersDTO saveUser(UsersDTO userDTO);
    void deleteUser(Integer id);
}