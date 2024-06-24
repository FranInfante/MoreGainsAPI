package com.example.MoreGains.repository;

import com.example.MoreGains.model.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findUsersByUsername(String username);

    Optional<Users> findByEmail(String identifier);

    Optional<Users> findByUsernameIgnoreCase(String identifier);
}