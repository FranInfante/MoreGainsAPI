package com.example.MoreGains.util;

public class UriConstants {
    public static final String HOST = "http://localhost";
    public static final String PORT = "8080";
    public static final String ANGULAR_PORT = ":4200";
    public static final String ANGULAR_HOST = HOST + ANGULAR_PORT;
    public static final String CONTEXT_PATH = "/api";
    public static final String VERSION = "/v1";
    public static final String BASE_URL = CONTEXT_PATH + VERSION;
    public static final String USERS = BASE_URL + "/users";
    public static final String USERS_BY_ID = "/{id}";
    public static final String USERS_SEARCH = "/search";
    public static final String USERS_LOGIN = "/login";
    public static final String USERS_AUTH = "/authenticate";
    public static final String USERS_INFO = "/information";
    public static final String WORKOUTS = "/workouts";
    public static final String WORKOUT_BY_ID = "/{id}";
    public static final String WORKOUT_EXERCISES = "/workoutexercises";
    public static final String WORKOUT_EXERCISES_FOR_WORKOUT = "/workout/{workoutId}/exercises";
    public static final String WORKOUT_EXERCISE_BY_ID = "/{id}";
    public static final String MUSCLE_GROUPS = "/musclegroups";
    public static final String MUSCLE_GROUP_BY_ID = "/{id}";
    public static final String MESOCYCLES = "/mesocycle";
    public static final String MESOCYCLE_BY_ID = "/{id}";
    public static final String FAVORITES = "/favorites";
    public static final String EXERCISES = "/exercises";
}
