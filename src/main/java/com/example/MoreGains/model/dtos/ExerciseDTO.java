package com.example.MoreGains.model.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDTO {
    private Integer id;
    private String name;
    private String description;
    private String videoUrl;
    private Boolean isAvailable = true;
    private MuscleGroupDTO muscleGroup;
}
