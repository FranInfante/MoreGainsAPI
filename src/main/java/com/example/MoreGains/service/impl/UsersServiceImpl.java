package com.example.MoreGains.service.impl;

import com.example.MoreGains.jwt.JwtUserDetailsService;
import com.example.MoreGains.model.dtos.UsersDTO;
import com.example.MoreGains.model.entities.Users;
import com.example.MoreGains.repository.UsersRepository;
import com.example.MoreGains.service.UsersService;
import com.example.MoreGains.util.UserJwt;
import com.example.MoreGains.util.UsersMapper;
import com.example.MoreGains.util.exceptions.UserException;
import com.example.MoreGains.util.jwt.JwtTokenUtil;
import com.example.MoreGains.util.messages.MessageConstants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

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
    public UsersDTO saveUser(UsersDTO userDTO) throws UserException {
        if (usersRepository.findByUsernameIgnoreCase(userDTO.getUsername()).isPresent()) {
            throw UserException.userNameExistingException();
        }
        if (usersRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw UserException.emailExistingException();
        }

        Users users = UsersMapper.userDTOToEntity(userDTO);
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Users savedUser = usersRepository.save(users);
        return UsersMapper.userEntityToDTO(savedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));
        user.setIsAvailable(false);
        usersRepository.save(user);
    }
    @Override
    public Optional<UsersDTO> updateUser(Integer userId, UsersDTO updateUser) throws Exception {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));
        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());
        user.setBio(updateUser.getBio());
        user.setPrivacySetting(updateUser.getPrivacySetting());
        user.setIsAvailable(updateUser.getIsAvailable());
        user.setPassword(passwordEncoder.encode(updateUser.getPassword()));

        Users savedUser = usersRepository.save(user);
        return Optional.of(UsersMapper.userEntityToDTO(savedUser));
    }

    @Override
    public List<UsersDTO> searchUsersByUserName(String username){

        List<Users> users = usersRepository.findUsersByUsername(username);
        return UsersMapper.listUserEntityToDTO(users);

    }

    @Override
    public Optional<UsersDTO> loginUser(String identifier, String password) {
        Optional<Users> user = usersRepository.findByEmail(identifier)
                .or(() -> usersRepository.findByUsernameIgnoreCase(identifier));

        return user.filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .map(UsersMapper::userEntityToDTO);
    }
    @Override
    public UserJwt createAuthenticationToken(UsersDTO authenticationRequest) throws Exception {
        String usernameOrEmail = authenticationRequest.getUsername() != null ? authenticationRequest.getUsername() : authenticationRequest.getEmail();
        authenticate(usernameOrEmail, authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(usernameOrEmail);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return UserJwt.builder().token(token).build();
    }

    private void authenticate(String usernameOrEmail, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Override
    public Optional<UsersDTO> getUserInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return Optional.empty();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Users user = usersRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new EntityNotFoundException(MessageConstants.USER_NOT_FOUND));

        UsersDTO userDTO = UsersMapper.userEntityToDTO(user);
        userDTO.setPassword(user.getPassword());

        return Optional.of(userDTO);
    }

}