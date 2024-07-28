package com.example.MoreGains.service;

import com.example.MoreGains.model.dtos.UsersDTO;
import com.example.MoreGains.util.UserJwt;
import com.example.MoreGains.util.exceptions.UserException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<UsersDTO> getAllUsers();
    UsersDTO getUserById(Integer id);
    UsersDTO saveUser(UsersDTO userDTO) throws UserException;
    void deleteUser(Integer id);
    List<UsersDTO> searchUsersByUserName(String username);
    Optional<UsersDTO> updateUser(Integer id, UsersDTO updateUser) throws Exception;
    Optional<UsersDTO> loginUser(String identifier, String password);
    UserJwt createAuthenticationToken(@RequestBody UsersDTO authenticationRequest) throws Exception;
    Optional<UsersDTO> getUserInformation();
}