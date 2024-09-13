package com.example.MoreGains.util;

public class UriConstants {
    public static final String HOST = "http://localhost";
    public static final String PORT = "8080";
    public static final String ANGULAR_PORT = ":4200";
    public static final String ANGULAR_HOST = HOST + ANGULAR_PORT;
    public static final String CONTEXT_PATH = "/api";
    public static final String VERSION = "/v1";
    public static final String BASE_URL = CONTEXT_PATH + VERSION;

    // Users endpoints
    public static final String USERS = BASE_URL + "/users";
    public static final String BY_ID = "/{id}";
    public static final String USERS_SEARCH = "/search";
    public static final String USERS_LOGIN = "/login";
    public static final String USERS_AUTH = "/authenticate";
    public static final String USERS_INFO = "/information";
    public static final String USERS_UPLOAD_PROFILE_PICTURE = "/upload-profile-picture";

    // Workouts and exercises endpoints
    public static final String WORKOUTS = BASE_URL + "/workouts";
    public static final String WORKOUT_EXERCISES = BASE_URL + "/workoutexercises";
    public static final String WORKOUT_EXERCISES_FOR_WORKOUT = BASE_URL + "/workout/{workoutId}/exercises";
    public static final String EXERCISES = BASE_URL + "/exercises";
    public static final String REORDER = "/{planId}/workouts/reorder";

    // Muscle groups endpoint
    public static final String MUSCLE_GROUPS = BASE_URL + "/musclegroups";

    // Favorites endpoint
    public static final String FAVORITES = BASE_URL + "/favorites";

    // Client-Trainer endpoints
    public static final String CLIENT_TRAINER = BASE_URL + "/clienttrainer";
    public static final String BY_TRAINER_ID = "/trainer/{trainerId}";
    public static final String BY_CLIENT_ID = "/client/{clientId}";

    // Plans endpoints
    public static final String PLANS = BASE_URL + "/plans";
    public static final String PLANS_BY_USER_ID = "/user/{userId}";
    public static final String UPDATE_NAME = "/{id}/name";
    public static final String WORKOUTS_IN_PLAN = "/{planId}/workouts";
    public static final String WORKOUT_EXERCISE_IN_PLAN = "/{planId}/workout/{workoutId}/exercise/{exerciseId}";
    public static final String WORKOUT_EXERCISE_IN_PLAN_CREATE = "/{planId}/workouts/{workoutId}/exercise";
    public static final String WORKOUT_IN_PLAN_CREATE = "/{planId}/workouts";
}
