package com.example.MoreGains.model.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetDTO {
    private Integer set;
    private Integer reps;
    private Double weight;
}
