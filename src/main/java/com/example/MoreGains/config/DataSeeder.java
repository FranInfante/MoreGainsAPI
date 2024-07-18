package com.example.MoreGains.config;

import com.example.MoreGains.model.entities.*;
import com.example.MoreGains.model.enums.PrivacySetting;
import com.example.MoreGains.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UsersRepository usersRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseRepository exerciseRepository;
    private final FavoriteRepository favoriteRepository;
    private final MesocycleRepository mesocycleRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ClientTrainerRepository clientTrainerRepository;

    private Optional<Users> user1;
    private Optional<Users> user2;
    private Optional<Users> trainer1;
    private Optional<Users> trainer2;
    private Optional<Users> client1;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    @Transactional
    public void seedData() {
        seedUsers();
        initializeUsers();
        seedMuscleGroups();
        seedExercises();
        seedFavorites();
        seedMesocycles();
        seedWorkouts();
        seedWorkoutExercises();
        seedClientTrainers();
    }

    private void initializeUsers() {
        user1 = usersRepository.findByUsernameIgnoreCase("user1");
        user2 = usersRepository.findByUsernameIgnoreCase("user2");
        trainer1 = usersRepository.findByUsernameIgnoreCase("trainer1");
        trainer2 = usersRepository.findByUsernameIgnoreCase("trainer2");
        client1 = usersRepository.findByUsernameIgnoreCase("client1");
    }

    @Transactional
    private void seedUsers() {
        Users[] users = {
                Users.builder()
                        .username("user1")
                        .email("user1@example.com")
                        .password("password")
                        .photoUrl("url_to_user1_photo")
                        .bio("Bio for user1")
                        .fitnessGoals("Fitness goals for user1")
                        .isTrainer(false)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PUBLIC)
                        .build(),
                Users.builder()
                        .username("user2")
                        .email("user2@example.com")
                        .password("password")
                        .photoUrl("url_to_user2_photo")
                        .bio("Bio for user2")
                        .fitnessGoals("Fitness goals for user2")
                        .isTrainer(false)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PUBLIC)
                        .build(),
                Users.builder()
                        .username("trainer1")
                        .email("trainer1@example.com")
                        .password("password")
                        .photoUrl("url_to_trainer1_photo")
                        .bio("Bio for trainer1")
                        .fitnessGoals("Fitness goals for trainer1")
                        .isTrainer(true)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PRIVATE)
                        .build(),
                Users.builder()
                        .username("trainer2")
                        .email("trainer2@example.com")
                        .password("password")
                        .photoUrl("url_to_trainer2_photo")
                        .bio("Bio for trainer2")
                        .fitnessGoals("Fitness goals for trainer2")
                        .isTrainer(true)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PRIVATE)
                        .build(),
                Users.builder()
                        .username("client1")
                        .email("client1@example.com")
                        .password("password")
                        .photoUrl("url_to_client1_photo")
                        .bio("Bio for client1")
                        .fitnessGoals("Fitness goals for client1")
                        .isTrainer(false)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PUBLIC)
                        .build()
        };

        usersRepository.saveAll(Arrays.asList(users));
    }

    @Transactional
    private void seedMuscleGroups() {
        MuscleGroup[] muscleGroups = {
                MuscleGroup.builder().name("Legs").isAvailable(true).build(),
                MuscleGroup.builder().name("Back").isAvailable(true).build(),
                MuscleGroup.builder().name("Chest").isAvailable(true).build(),
                MuscleGroup.builder().name("Arms").isAvailable(true).build(),
                MuscleGroup.builder().name("Shoulders").isAvailable(true).build()
        };

        muscleGroupRepository.saveAll(Arrays.asList(muscleGroups));
    }

    @Transactional
    public void seedExercises() {
        MuscleGroup legs = muscleGroupRepository.findByNameIgnoreCase("Legs");
        MuscleGroup back = muscleGroupRepository.findByNameIgnoreCase("Back");
        MuscleGroup chest = muscleGroupRepository.findByNameIgnoreCase("Chest");
        MuscleGroup arms = muscleGroupRepository.findByNameIgnoreCase("Arms");
        MuscleGroup shoulders = muscleGroupRepository.findByNameIgnoreCase("Shoulders");

        List<Exercise> exercises = Arrays.asList(
                Exercise.builder()
                        .name("Squats")
                        .description("Lower body exercise targeting quadriceps, hamstrings, and glutes.")
                        .videoUrl("https://example.com/squats-video")
                        .isAvailable(true)
                        .muscleGroup(legs)
                        .build(),
                Exercise.builder()
                        .name("Deadlifts")
                        .description("Compound exercise involving lifting a barbell off the ground.")
                        .videoUrl("https://example.com/deadlifts-video")
                        .isAvailable(true)
                        .muscleGroup(back)
                        .build(),
                Exercise.builder()
                        .name("Bench Press")
                        .description("Upper body exercise focusing on chest muscles.")
                        .videoUrl("https://example.com/bench-press-video")
                        .isAvailable(true)
                        .muscleGroup(chest)
                        .build(),
                Exercise.builder()
                        .name("Bicep Curls")
                        .description("Isolation exercise targeting biceps.")
                        .videoUrl("https://example.com/bicep-curls-video")
                        .isAvailable(true)
                        .muscleGroup(arms)
                        .build(),
                Exercise.builder()
                        .name("Shoulder Press")
                        .description("Exercise targeting shoulder muscles.")
                        .videoUrl("https://example.com/shoulder-press-video")
                        .isAvailable(true)
                        .muscleGroup(shoulders)
                        .build()
        );

        exerciseRepository.saveAll(exercises);
    }

    @Transactional
    private void seedFavorites() {

        user1.ifPresent(u -> {
            Exercise squat = exerciseRepository.findByNameIgnoreCase("Squats");
            Favorite favorite1 = Favorite.builder().users(u).exercise(squat).isAvailable(true).build();
            favoriteRepository.save(favorite1);
        });

        user2.ifPresent(u -> {
            Exercise deadlifts = exerciseRepository.findByNameIgnoreCase("Deadlifts");
            Favorite favorite2 = Favorite.builder().users(u).exercise(deadlifts).isAvailable(true).build();
            favoriteRepository.save(favorite2);
        });

        client1.ifPresent(u -> {
            Exercise benchPress = exerciseRepository.findByNameIgnoreCase("Bench Press");
            Favorite favorite3 = Favorite.builder().users(u).exercise(benchPress).isAvailable(true).build();
            favoriteRepository.save(favorite3);
        });

        trainer1.ifPresent(u -> {
            Exercise bicepCurls = exerciseRepository.findByNameIgnoreCase("Bicep Curls");
            Favorite favorite4 = Favorite.builder().users(u).exercise(bicepCurls).isAvailable(true).build();
            favoriteRepository.save(favorite4);
        });

        trainer2.ifPresent(u -> {
            Exercise shoulderPress = exerciseRepository.findByNameIgnoreCase("Shoulder Press");
            Favorite favorite5 = Favorite.builder().users(u).exercise(shoulderPress).isAvailable(true).build();
            favoriteRepository.save(favorite5);
        });
    }

    @Transactional
    private void seedMesocycles() {
        trainer1.ifPresent(trainer -> {
            Mesocycle[] mesocycles = {
                    Mesocycle.builder()
                            .users(trainer)
                            .name("Summer Shred")
                            .startDate(LocalDate.now()).endDate(LocalDate.now().plusMonths(3))
                            .description("Summer Shred")
                            .isAvailable(true)
                            .build(),
                    Mesocycle.builder()
                            .users(trainer)
                            .name("Winter Bulk")
                            .startDate(LocalDate.now().minusMonths(2))
                            .endDate(LocalDate.now())
                            .description("Winter Bulk")
                            .isAvailable(true)
                            .build()
            };

            mesocycleRepository.saveAll(Arrays.asList(mesocycles));
        });
    }

    @Transactional
    private void seedWorkouts() {
        client1.ifPresent(client -> {
            Workout[] workouts = {
                    Workout.builder().users(client).date(LocalDate.now()).name("Leg Day").description("Leg workout focusing on quadriceps, hamstrings, and calves.").isAvailable(true).build(),
                    Workout.builder().users(client).date(LocalDate.now().minusDays(1)).name("Back and Biceps").description("Back and biceps workout targeting back muscles and biceps.").isAvailable(true).build(),
                    Workout.builder().users(client).date(LocalDate.now().minusDays(2)).name("Chest and Triceps").description("Chest and triceps workout focusing on chest muscles and triceps.").isAvailable(true).build()
            };

            workoutRepository.saveAll(Arrays.asList(workouts));
        });
    }º

    @Transactional
    private void seedWorkoutExercises() {
        Workout legDay = workoutRepository.findByNameIgnoreCase("Leg Day");
        Exercise squats = exerciseRepository.findByNameIgnoreCase("Squats");

        if (legDay != null && squats != null) {
            WorkoutExercise legDaySquats = WorkoutExercise.builder()
                    .workout(legDay)
                    .exercise(squats)
                    .sets(4)
                    .reps(10)
                    .weight(100.0)
                    .build();

            workoutExerciseRepository.save(legDaySquats);
        }

        Workout backAndBiceps = workoutRepository.findByNameIgnoreCase("Back and Biceps");
        Exercise deadlifts = exerciseRepository.findByNameIgnoreCase("Deadlifts");

        if (backAndBiceps != null && deadlifts != null) {
            WorkoutExercise backAndBicepsDeadlifts = WorkoutExercise.builder()
                    .workout(backAndBiceps)
                    .exercise(deadlifts)
                    .sets(3)
                    .reps(12)
                    .weight(120.0)
                    .build();

            workoutExerciseRepository.save(backAndBicepsDeadlifts);
        }

        Workout chestAndTriceps = workoutRepository.findByNameIgnoreCase("Chest and Triceps");
        Exercise benchPress = exerciseRepository.findByNameIgnoreCase("Bench Press");

        if (chestAndTriceps != null && benchPress != null) {
            WorkoutExercise chestAndTricepsBenchPress = WorkoutExercise.builder()
                    .workout(chestAndTriceps)
                    .exercise(benchPress)
                    .sets(4)
                    .reps(8)
                    .weight(80.0)
                    .build();

            workoutExerciseRepository.save(chestAndTricepsBenchPress);
        }
    }

    @Transactional
    private void seedClientTrainers() {
        // Assume you have predefined Users objects for trainers and clients
        trainer1.ifPresent(trainer -> {
            client1.ifPresent(client -> {
                ClientTrainer[] clientTrainers = {
                        ClientTrainer.builder().trainer(trainer).client(client).build(),
                        ClientTrainer.builder().trainer(usersRepository.findByUsernameIgnoreCase("trainer2").orElse(null)).client(usersRepository.findByUsernameIgnoreCase("user2").orElse(null)).build(),
                        ClientTrainer.builder().trainer(usersRepository.findByUsernameIgnoreCase("trainer2").orElse(null)).client(usersRepository.findByUsernameIgnoreCase("client1").orElse(null)).build(),
                        ClientTrainer.builder().trainer(usersRepository.findByUsernameIgnoreCase("trainer1").orElse(null)).client(usersRepository.findByUsernameIgnoreCase("user1").orElse(null)).build(),
                        ClientTrainer.builder().trainer(usersRepository.findByUsernameIgnoreCase("trainer1").orElse(null)).client(usersRepository.findByUsernameIgnoreCase("trainer2").orElse(null)).build()
                };

                clientTrainerRepository.saveAll(Arrays.asList(clientTrainers));
            });
        });
    }
}
