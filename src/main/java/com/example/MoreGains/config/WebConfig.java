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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
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
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.POST, UriConstants.USERS).permitAll()
                        .requestMatchers(UriConstants.USERS + UriConstants.USERS_LOGIN).permitAll()
                        .requestMatchers(UriConstants.USERS + UriConstants.USERS_AUTH).permitAll()
                        .requestMatchers(UriConstants.USERS + UriConstants.BY_ID).permitAll()
                        .requestMatchers(HttpMethod.GET, UriConstants.PLANS + "/**").permitAll()
                        .requestMatchers(UriConstants.PLANS + UriConstants.PLANS_BY_USER_ID).permitAll()
                        .requestMatchers(UriConstants.PLANS + UriConstants.BY_ID).permitAll()
                        .requestMatchers(HttpMethod.POST, UriConstants.PLANS).permitAll()
                        .requestMatchers(HttpMethod.POST, UriConstants.PLANS + UriConstants.WORKOUTS_IN_PLAN).permitAll()
                        .requestMatchers(HttpMethod.GET, UriConstants.PLANS + UriConstants.WORKOUT_EXERCISE_IN_PLAN).permitAll()
                        .requestMatchers(HttpMethod.DELETE, UriConstants.PLANS + UriConstants.WORKOUT_EXERCISE_IN_PLAN).permitAll()
                        .requestMatchers(UriConstants.PLANS).permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, UriConstants.PLANS + UriConstants.WORKOUT_EXERCISE_IN_PLAN_CREATE).permitAll()
                        .requestMatchers(HttpMethod.GET, UriConstants.EXERCISES).permitAll()
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
