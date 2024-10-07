package com.example.MoreGains.model.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutLogSearchRequest {
    private Integer userId;
    private Boolean isEditing;
}
