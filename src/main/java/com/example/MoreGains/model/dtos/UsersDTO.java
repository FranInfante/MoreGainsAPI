package com.example.MoreGains.model.dtos;

import com.example.MoreGains.model.enums.PrivacySetting;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String photoUrl;
    private String bio;
    private String fitnessGoals;
    private Boolean isTrainer = false;
    private Boolean isAvailable = true;

    @Column(nullable = false)
    private PrivacySetting privacySetting = PrivacySetting.PUBLIC;
}