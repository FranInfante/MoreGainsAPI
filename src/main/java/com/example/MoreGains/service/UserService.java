package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.UsersDTO;

import java.util.List;

public interface UserService {
    List<UsersDTO> getAllUsers();
    UsersDTO getUserById(Long id);
    UsersDTO saveUser(UsersDTO user);
    void deleteUser(Long id);
}