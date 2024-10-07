package com.example.MoreGains.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @OneToMany(mappedBy = "workoutLog", cascade = CascadeType.ALL)
    private List<WorkoutLogExercise> exercises;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = true)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isEditing;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Method to remove an exercise
    public void removeExercise(WorkoutLogExercise exercise) {
        this.exercises.remove(exercise);
        exercise.setWorkoutLog(null);  // Break the relationship to avoid issues
    }
}
