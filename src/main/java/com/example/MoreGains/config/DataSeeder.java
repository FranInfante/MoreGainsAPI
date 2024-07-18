package com.example.MoreGains.config;

import com.example.MoreGains.model.entities.*;
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


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    @Transactional
    public void seedData() {
        seedUsers();
        seedMuscleGroups();
        seedExercises();
        seedFavorites();
        seedMesocycles();
        seedWorkouts();
        seedWorkoutExercises();
        seedClientTrainers();
    }

    @Transactional
    private void seedUsers() {

        Users user1 = Users.builder()
                .username("user1")
                .email("user1@example.com")
                .password("password")
                .build();

        Users user2 = Users.builder()
                .username("user2")
                .email("user2@example.com")
                .password("password")
                .build();

        Users trainer1 = Users.builder()
                .username("trainer1")
                .email("trainer1@example.com")
                .password("password")
                .isTrainer(true)
                .build();

        Users trainer2 = Users.builder()
                .username("trainer2")
                .email("trainer2@example.com")
                .password("password")
                .isTrainer(true)
                .build();

        Users client1 = Users.builder()
                .username("client1")
                .email("client1@example.com")
                .password("password")
                .build();

        usersRepository.saveAll(Arrays.asList(user1, user2, trainer1, trainer2, client1));
    }

    @Transactional
    private void seedMuscleGroups() {
        MuscleGroup[] muscleGroups = {
                MuscleGroup.builder().name("Legs").build(),
                MuscleGroup.builder().name("Back").build(),
                MuscleGroup.builder().name("Chest").build(),
                MuscleGroup.builder().name("Arms").build(),
                MuscleGroup.builder().name("Shoulders").build()
        };

        muscleGroupRepository.saveAll(Arrays.asList(muscleGroups));
    }

    @Transactional
    private void seedExercises() {
        MuscleGroup legs = muscleGroupRepository.findByNameIgnoreCase("Legs");
        MuscleGroup back = muscleGroupRepository.findByNameIgnoreCase("Back");
        MuscleGroup chest = muscleGroupRepository.findByNameIgnoreCase("Chest");
        MuscleGroup arms = muscleGroupRepository.findByNameIgnoreCase("Arms");
        MuscleGroup shoulders = muscleGroupRepository.findByNameIgnoreCase("Shoulders");

        List<Exercise> exercises = Arrays.asList(
                Exercise.builder().name("Squats").muscleGroup(legs).build(),
                Exercise.builder().name("Deadlifts").muscleGroup(back).build(),
                Exercise.builder().name("Bench Press").muscleGroup(chest).build(),
                Exercise.builder().name("Bicep Curls").muscleGroup(arms).build(),
                Exercise.builder().name("Shoulder Press").muscleGroup(shoulders).build()
        );

        exerciseRepository.saveAll(exercises);
    }

    @Transactional
    private void seedFavorites() {
        Exercise squat = exerciseRepository.findByNameIgnoreCase("Squats");

        // Fetch users using Optional and handle appropriately
        Optional<Users> optionalUser1 = usersRepository.findByUsernameIgnoreCase("user1");
        optionalUser1.ifPresent(user1 -> {
            Favorite favorite1 = Favorite.builder().users(user1).exercise(squat).build();
            favoriteRepository.save(favorite1);
        });

        Optional<Users> optionalUser2 = usersRepository.findByUsernameIgnoreCase("user2");
        optionalUser2.ifPresent(user2 -> {
            Exercise deadlifts = exerciseRepository.findByNameIgnoreCase("Deadlifts");
            Favorite favorite2 = Favorite.builder().users(user2).exercise(deadlifts).build();
            favoriteRepository.save(favorite2);
        });

        Optional<Users> optionalClient1 = usersRepository.findByUsernameIgnoreCase("client1");
        optionalClient1.ifPresent(client1 -> {
            Exercise benchPress = exerciseRepository.findByNameIgnoreCase("Bench Press");
            Favorite favorite3 = Favorite.builder().users(client1).exercise(benchPress).build();
            favoriteRepository.save(favorite3);
        });

        Optional<Users> optionalTrainer1 = usersRepository.findByUsernameIgnoreCase("trainer1");
        optionalTrainer1.ifPresent(trainer1 -> {
            Exercise bicepCurls = exerciseRepository.findByNameIgnoreCase("Bicep Curls");
            Favorite favorite4 = Favorite.builder().users(trainer1).exercise(bicepCurls).build();
            favoriteRepository.save(favorite4);
        });

        Optional<Users> optionalTrainer2 = usersRepository.findByUsernameIgnoreCase("trainer2");
        optionalTrainer2.ifPresent(trainer2 -> {
            Exercise shoulderPress = exerciseRepository.findByNameIgnoreCase("Shoulder Press");
            Favorite favorite5 = Favorite.builder().users(trainer2).exercise(shoulderPress).build();
            favoriteRepository.save(favorite5);
        });
    }

    @Transactional
    private void seedMesocycles() {
        // Assume you have predefined Users objects
        Optional<Users> trainer1 = usersRepository.findByUsernameIgnoreCase("trainer1");

        Mesocycle[] mesocycles = {
                Mesocycle.builder().users(trainer1).name("Summer Shred").startDate(LocalDate.now()).endDate(LocalDate.now().plusMonths(3)).build(),
                Mesocycle.builder().users(trainer1).name("Winter Bulk").startDate(LocalDate.now().minusMonths(2)).endDate(LocalDate.now()).build()
        };

        mesocycleRepository.saveAll(Arrays.asList(mesocycles));
    }

    @Transactional
    private void seedWorkouts() {
        // Assume you have predefined Users objects
        Users client1 = usersRepository.findByUsernameIgnoreCase("client1");

        Workout[] workouts = {
                Workout.builder().users(client1).date(LocalDate.now()).name("Leg Day").build(),
                Workout.builder().users(client1).date(LocalDate.now().minusDays(1)).name("Back and Biceps").build(),
                Workout.builder().users(client1).date(LocalDate.now().minusDays(2)).name("Chest and Triceps").build()
        };

        workoutRepository.saveAll(Arrays.asList(workouts));
    }

    @Transactional
    private void seedWorkoutExercises() {
        // Assume you have predefined Workout and Exercise objects
        Workout legDay = workoutRepository.findByNameIgnoreCase("Leg Day");
        Exercise squats = exerciseRepository.findByNameIgnoreCase("Squats");

        WorkoutExercise[] workoutExercises = {
                WorkoutExercise.builder().workout(legDay).exercise(squats).sets(4).reps(10).weight(100.0).build(),
                WorkoutExercise.builder().workout(workoutRepository.findByNameIgnoreCase("Back and Biceps")).exercise(exerciseRepository.findByNameIgnoreCase("Deadlifts")).sets(3).reps(12).weight(120.0).build(),
                WorkoutExercise.builder().workout(workoutRepository.findByNameIgnoreCase("Chest and Triceps")).exercise(exerciseRepository.findByNameIgnoreCase("Bench Press")).sets(4).reps(8).weight(80.0).build()
        };

        workoutExerciseRepository.saveAll(Arrays.asList(workoutExercises));
    }

    @Transactional
    private void seedClientTrainers() {
        // Assume you have predefined Users objects for trainers and clients
        Users trainer1 = usersRepository.findByUsernameIgnoreCase("trainer1");
        Users client1 = usersRepository.findByUsernameIgnoreCase("client1");

        ClientTrainer[] clientTrainers = {
                ClientTrainer.builder().trainer(trainer1).client(client1).build(),
                ClientTrainer.builder().trainer(usersRepository.findByUsernameIgnoreCase("trainer2")).client(usersRepository.findByUsernameIgnoreCase("user2")).build(),
                ClientTrainer.builder().trainer(usersRepository.findByUsernameIgnoreCase("trainer2")).client(usersRepository.findByUsernameIgnoreCase("client1")).build(),
                ClientTrainer.builder().trainer(usersRepository.findByUsernameIgnoreCase("trainer1")).client(usersRepository.findByUsernameIgnoreCase("user1")).build(),
                ClientTrainer.builder().trainer(usersRepository.findByUsernameIgnoreCase("trainer1")).client(usersRepository.findByUsernameIgnoreCase("trainer2")).build()
        };

        clientTrainerRepository.saveAll(Arrays.asList(clientTrainers));
    }
}
