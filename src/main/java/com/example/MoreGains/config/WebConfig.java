package com.example.MoreGains.config;

import com.example.MoreGains.jwt.JwtAuthenticationEntryPoint;
import com.example.MoreGains.jwt.JwtAuthenticationFilter;
import com.example.MoreGains.util.UriConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Allow access to static resources
                        .requestMatchers("/uploads/**").permitAll()

                        // Allow access to user-related endpoints
                        .requestMatchers(HttpMethod.POST, UriConstants.USERS).permitAll() // User registration
                        .requestMatchers(UriConstants.USERS + UriConstants.USERS_LOGIN).permitAll() // User login
                        .requestMatchers(UriConstants.USERS + UriConstants.USERS_AUTH).permitAll() // User authentication
                        .requestMatchers(UriConstants.USERS + UriConstants.BY_ID).permitAll() // Access user by ID

                        // Allow access to plans-related endpoints
                        .requestMatchers(HttpMethod.GET, UriConstants.PLANS + "/**").permitAll() // Get all plans and plan by ID
                        .requestMatchers(UriConstants.PLANS + UriConstants.PLANS_BY_USER_ID).permitAll() // Get plans by user ID
                        .requestMatchers(HttpMethod.POST, UriConstants.PLANS).permitAll() // Create a new plan
                        .requestMatchers(HttpMethod.POST, UriConstants.PLANS + UriConstants.WORKOUTS_IN_PLAN).permitAll() // Add workout to plan
                        .requestMatchers(HttpMethod.GET, UriConstants.PLANS + UriConstants.WORKOUT_EXERCISE_IN_PLAN).permitAll() // Get workout exercise in plan
                        .requestMatchers(HttpMethod.DELETE, UriConstants.PLANS + UriConstants.WORKOUT_EXERCISE_IN_PLAN).permitAll() // Delete workout exercise in plan
                        .requestMatchers(HttpMethod.DELETE, UriConstants.PLANS + UriConstants.BY_ID).permitAll() // Delete plan by ID
                        .requestMatchers(HttpMethod.POST, UriConstants.PLANS + UriConstants.WORKOUT_EXERCISE_IN_PLAN_CREATE).permitAll() // Add exercise to workout in plan
                        .requestMatchers(HttpMethod.POST, UriConstants.PLANS + UriConstants.WORKOUT_IN_PLAN_CREATE).permitAll()
                        .requestMatchers(HttpMethod.PATCH, UriConstants.PLANS + UriConstants.UPDATE_NAME).permitAll()
                        .requestMatchers(UriConstants.PLANS).permitAll()
                        .requestMatchers(HttpMethod.DELETE, UriConstants.PLANS + UriConstants.WORKOUT_IN_PLAN_DELETE).permitAll()

                        .requestMatchers(HttpMethod.PUT, UriConstants.PLANS + UriConstants.REORDER).permitAll()

                        // Allow access to exercise-related endpoints
                        .requestMatchers(HttpMethod.GET, UriConstants.EXERCISES).permitAll() // Get all exercises

                        // Allow access to all OPTIONS requests (CORS pre-flight)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
