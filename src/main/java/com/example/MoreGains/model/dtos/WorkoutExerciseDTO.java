package com.example.MoreGains.model.dtos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutExerciseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private WorkoutDTO workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseDTO exercise;

    private Integer sets;
    private Integer reps;
    private Double weight;
}