package com.example.MoreGains.controller.impl;

import com.example.MoreGains.controller.UsersApi;
import com.example.MoreGains.model.dtos.UsersDTO;
import com.example.MoreGains.model.enums.PrivacySetting;
import com.example.MoreGains.service.UsersService;
import com.example.MoreGains.util.UserJwt;
import com.example.MoreGains.util.exceptions.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UsersService usersService;

    @Override
    public ResponseEntity<UsersDTO> getUserById(Integer id) {
        UsersDTO user = usersService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<UsersDTO>> searchUsers(String username) {
        List<UsersDTO> listName = usersService.searchUsersByUserName(username);
        return ResponseEntity.ok(listName);
    }

    @Override
    public ResponseEntity<UsersDTO> createUser(UsersDTO newUser) throws Exception {
        if (newUser.getPrivacySetting() == null) {
            newUser.setPrivacySetting(PrivacySetting.PUBLIC);
        }
        UsersDTO createdUser = usersService.saveUser(newUser);
        return ResponseEntity.ok(createdUser);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateUser(Integer id, UsersDTO updateUser) throws Exception {
        usersService.updateUser(id, updateUser);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UsersDTO> loginUser(@RequestBody UsersDTO userDTO) {
        String identifier = userDTO.getEmail() != null ? userDTO.getEmail() : userDTO.getUsername();
        Optional<UsersDTO> loggedInUser = usersService.loginUser(identifier, userDTO.getPassword());
        return loggedInUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<UserJwt> authUser(@RequestBody UsersDTO userDTO) throws Exception {
        UserJwt userJwt = usersService.createAuthenticationToken(userDTO);

        return ResponseEntity.ok(userJwt);
    }
    @Override
    public ResponseEntity<UsersDTO> getUserInfo() {
        UsersDTO usersDTO = usersService.getUserInformation().get();
        return ResponseEntity.ok(usersDTO);
    }

    @Override
    public ResponseEntity<String> uploadProfilePicture(MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Unauthorized\"}");
            }

            Object principal = authentication.getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                username = (String) principal;
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Principal is not of expected type\"}");
            }

            UsersDTO userDTO = usersService.findUserByUsername(username);
            if (userDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"User not found\"}");
            }

            String imageUrl = usersService.uploadProfilePicture(userDTO.getId(), file);
            return ResponseEntity.ok("{\"imageUrl\": \"" + imageUrl + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"" + e.getMessage() + "\"}");
        }

    }
}
