package com.example.MoreGains.model.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutExerciseDTO {
    private Integer id;
    private Integer exerciseId;
    private String exerciseName;
}
