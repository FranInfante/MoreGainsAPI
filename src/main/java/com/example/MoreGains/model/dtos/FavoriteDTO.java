package com.example.MoreGains.model.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersDTO users;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseDTO exercise;
}