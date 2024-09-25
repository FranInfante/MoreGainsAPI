package com.example.MoreGains.model.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutLogExerciseDTO {
    private Integer id;
    private Integer exerciseId;
    private Integer sets;
    private Integer reps;
    private Double weight;
    private String notes;
    private Integer workoutLogId;

}
