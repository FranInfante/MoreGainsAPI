package com.example.MoreGains.config;

import com.example.MoreGains.model.entities.*;
import com.example.MoreGains.model.enums.MuscleGroupType;
import com.example.MoreGains.model.enums.PrivacySetting;
import com.example.MoreGains.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final PlanRepository planRepository;
    private final ClientTrainerRepository clientTrainerRepository;
    private final PasswordEncoder passwordEncoder;

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
        seedWorkouts();
        seedWorkoutExercises();
        seedPlans();
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
                        .password(passwordEncoder.encode("password"))
                        .bio("Bio for user1")
                        .fitnessGoals("Fitness goals for user1")
                        .isTrainer(false)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PUBLIC)
                        .build(),
                Users.builder()
                        .username("user2")
                        .email("user2@example.com")
                        .password(passwordEncoder.encode("password"))
                        .bio("Bio for user2")
                        .fitnessGoals("Fitness goals for user2")
                        .isTrainer(false)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PUBLIC)
                        .build(),
                Users.builder()
                        .username("trainer1")
                        .email("trainer1@example.com")
                        .password(passwordEncoder.encode("password"))
                        .bio("Bio for trainer1")
                        .fitnessGoals("Fitness goals for trainer1")
                        .isTrainer(true)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PRIVATE)
                        .build(),
                Users.builder()
                        .username("trainer2")
                        .email("trainer2@example.com")
                        .password(passwordEncoder.encode("password"))
                        .bio("Bio for trainer2")
                        .fitnessGoals("Fitness goals for trainer2")
                        .isTrainer(true)
                        .isAvailable(true)
                        .privacySetting(PrivacySetting.PRIVATE)
                        .build(),
                Users.builder()
                        .username("client1")
                        .email("client1@example.com")
                        .password(passwordEncoder.encode("password"))
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
                MuscleGroup.builder().name(MuscleGroupType.LEGS).build(),
                MuscleGroup.builder().name(MuscleGroupType.BACK).build(),
                MuscleGroup.builder().name(MuscleGroupType.CHEST).build(),
                MuscleGroup.builder().name(MuscleGroupType.ARMS).build(),
                MuscleGroup.builder().name(MuscleGroupType.SHOULDERS).build(),
                MuscleGroup.builder().name(MuscleGroupType.HAMSTRINGS).build(),
                MuscleGroup.builder().name(MuscleGroupType.QUADS).build(),
                MuscleGroup.builder().name(MuscleGroupType.GLUTES).build(),
                MuscleGroup.builder().name(MuscleGroupType.BICEPS).build(),
                MuscleGroup.builder().name(MuscleGroupType.FOREARMS).build(),
                MuscleGroup.builder().name(MuscleGroupType.OTHER).build()
        };

        muscleGroupRepository.saveAll(Arrays.asList(muscleGroups));
    }

    @Transactional
    public void seedExercises() {
        MuscleGroup legs = muscleGroupRepository.findByName(MuscleGroupType.LEGS);
        MuscleGroup back = muscleGroupRepository.findByName(MuscleGroupType.BACK);
        MuscleGroup chest = muscleGroupRepository.findByName(MuscleGroupType.CHEST);
        MuscleGroup arms = muscleGroupRepository.findByName(MuscleGroupType.ARMS);
        MuscleGroup shoulders = muscleGroupRepository.findByName(MuscleGroupType.SHOULDERS);

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
            Optional<Exercise> squat = exerciseRepository.findByNameIgnoreCase("Squats");
            squat.ifPresent(exercise -> {
                Favorite favorite1 = Favorite.builder().users(u).exercise(exercise).isAvailable(true).build();
                favoriteRepository.save(favorite1);
            });
        });

        user2.ifPresent(u -> {
            Optional<Exercise> deadlifts = exerciseRepository.findByNameIgnoreCase("Deadlifts");
            deadlifts.ifPresent(exercise -> {
                Favorite favorite2 = Favorite.builder().users(u).exercise(exercise).isAvailable(true).build();
                favoriteRepository.save(favorite2);
            });
        });

        client1.ifPresent(u -> {
            Optional<Exercise> benchPress = exerciseRepository.findByNameIgnoreCase("Bench Press");
            benchPress.ifPresent(exercise -> {
                Favorite favorite3 = Favorite.builder().users(u).exercise(exercise).isAvailable(true).build();
                favoriteRepository.save(favorite3);
            });
        });

        trainer1.ifPresent(u -> {
            Optional<Exercise> bicepCurls = exerciseRepository.findByNameIgnoreCase("Bicep Curls");
            bicepCurls.ifPresent(exercise -> {
                Favorite favorite4 = Favorite.builder().users(u).exercise(exercise).isAvailable(true).build();
                favoriteRepository.save(favorite4);
            });
        });

        trainer2.ifPresent(u -> {
            Optional<Exercise> shoulderPress = exerciseRepository.findByNameIgnoreCase("Shoulder Press");
            shoulderPress.ifPresent(exercise -> {
                Favorite favorite5 = Favorite.builder().users(u).exercise(exercise).isAvailable(true).build();
                favoriteRepository.save(favorite5);
            });
        });
    }

    @Transactional
    private void seedWorkouts() {
        user1.ifPresent(u -> {
            Workout workout1 = Workout.builder()
                    .user(u)
                    .date(LocalDate.now())
                    .name("Leg Day")
                    .description("Leg Day Workout")
                    .isAvailable(true)
                    .build();

            workoutRepository.save(workout1);

            Workout workout2 = Workout.builder()
                    .user(u)
                    .date(LocalDate.now().plusDays(1))
                    .name("Back and Biceps")
                    .description("Back and Biceps Workout")
                    .isAvailable(true)
                    .build();

            workoutRepository.save(workout2);

            Workout workout3 = Workout.builder()
                    .user(u)
                    .date(LocalDate.now().plusDays(2))
                    .name("Chest and Triceps")
                    .description("Chest and Triceps Workout")
                    .isAvailable(true)
                    .build();

            workoutRepository.save(workout3);
        });
    }

    @Transactional
    private void seedWorkoutExercises() {
        Optional<Workout> legDay = workoutRepository.findByNameIgnoreCase("Leg Day");
        Optional<Exercise> squats = exerciseRepository.findByNameIgnoreCase("Squats");

        if (legDay.isPresent() && squats.isPresent()) {
            WorkoutExercise legDaySquats = WorkoutExercise.builder()
                    .workout(legDay.get())
                    .exercise(squats.get())
                    .sets(4)
                    .reps(10)
                    .weight(100.0)
                    .build();

            workoutExerciseRepository.save(legDaySquats);
        }

        Optional<Workout> backAndBiceps = workoutRepository.findByNameIgnoreCase("Back and Biceps");
        Optional<Exercise> deadlifts = exerciseRepository.findByNameIgnoreCase("Deadlifts");

        if (backAndBiceps.isPresent() && deadlifts.isPresent()) {
            WorkoutExercise backAndBicepsDeadlifts = WorkoutExercise.builder()
                    .workout(backAndBiceps.get())
                    .exercise(deadlifts.get())
                    .sets(3)
                    .reps(12)
                    .weight(120.0)
                    .build();

            workoutExerciseRepository.save(backAndBicepsDeadlifts);
        }

        Optional<Workout> chestAndTriceps = workoutRepository.findByNameIgnoreCase("Chest and Triceps");
        Optional<Exercise> benchPress = exerciseRepository.findByNameIgnoreCase("Bench Press");

        if (chestAndTriceps.isPresent() && benchPress.isPresent()) {
            WorkoutExercise chestAndTricepsBenchPress = WorkoutExercise.builder()
                    .workout(chestAndTriceps.get())
                    .exercise(benchPress.get())
                    .sets(4)
                    .reps(8)
                    .weight(80.0)
                    .build();

            workoutExerciseRepository.save(chestAndTricepsBenchPress);
        }
    }

    @Transactional
    private void seedPlans() {
        user1.ifPresent(u -> {
            List<Workout> workouts = workoutRepository.findAllByUser(u);

            Plan plan1 = Plan.builder()
                    .name("Beginner Plan")
                    .user(u)
                    .build();
            if (!workouts.isEmpty()) {
                plan1.setWorkouts(Arrays.asList(workouts.get(0))); // Associate a unique workout to this plan
            }
            planRepository.save(plan1);

            Plan plan2 = Plan.builder()
                    .name("Intermediate Plan")
                    .user(u)
                    .build();
            if (workouts.size() > 1) {
                plan2.setWorkouts(Arrays.asList(workouts.get(1))); // Associate a unique workout to this plan
            }
            planRepository.save(plan2);
        });

        user2.ifPresent(u -> {
            List<Workout> workouts = workoutRepository.findAllByUser(u);

            Plan plan3 = Plan.builder()
                    .name("Advanced Plan")
                    .user(u)
                    .build();
            if (!workouts.isEmpty()) {
                plan3.setWorkouts(Arrays.asList(workouts.get(0))); // Ensure the workout list is not empty
            }
            planRepository.save(plan3);
        });
    }

    @Transactional
    private void seedClientTrainers() {
        trainer1.ifPresent(trainer -> {
            client1.ifPresent(client -> {
                ClientTrainer clientTrainer1 = ClientTrainer.builder()
                        .trainer(trainer)
                        .client(client)
                        .build();

                clientTrainerRepository.save(clientTrainer1);
            });

            user1.ifPresent(client -> {
                ClientTrainer clientTrainer2 = ClientTrainer.builder()
                        .trainer(trainer)
                        .client(client)
                        .build();

                clientTrainerRepository.save(clientTrainer2);
            });
        });

        trainer2.ifPresent(trainer -> {
            user2.ifPresent(client -> {
                ClientTrainer clientTrainer3 = ClientTrainer.builder()
                        .trainer(trainer)
                        .client(client)
                        .build();

                clientTrainerRepository.save(clientTrainer3);
            });
        });
    }
}
