package com.example.MoreGains.service.impl;

import com.example.MoreGains.model.dtos.UsersDTO;
import com.example.MoreGains.model.entities.Users;
import com.example.MoreGains.repository.UsersRepository;
import com.example.MoreGains.service.UsersService;
import com.example.MoreGains.util.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public List<UsersDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return UsersMapper.listUserEntityToDTO(users);
    }

    @Override
    public UsersDTO getUserById(Integer id) {
        Users users = usersRepository.findById(id).orElse(null);
        return UsersMapper.userEntityToDTO(users);
    }

    @Override
    public UsersDTO saveUser(UsersDTO userDTO) {
        Users users = UsersMapper.userDTOToEntity(userDTO);
        Users savedUser = usersRepository.save(users);
        return UsersMapper.userEntityToDTO(savedUser);
    }

    @Override
    public void deleteUser(Integer id) {
        usersRepository.deleteById(id);
    }
}