package com.example.MoreGains.model.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDTO {
    private Integer id;
    private LocalDate date;
    private String name;
    private String description;
    private Boolean isAvailable = true;
    private List<WorkoutExerciseDTO> workoutExercises;
}
