package com.example.MoreGains.model.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutExerciseDTO {
    private Integer id;
    private WorkoutDTO workout;
    private ExerciseDTO exercise;
    private Integer reps;
    private Integer sets;
    private Double weight;
}
