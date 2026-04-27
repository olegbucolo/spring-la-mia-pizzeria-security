package org.exercises.java.spring_la_mia_pizzeria_security.service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(DatabaseUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                // /pizzas -> GET index USER, ADMIN --
                // /pizzas/id -> SHOW (shows details) USER, ADMIN --
                // /pizzas/id -> DELETE ADMIN OK
                // /pizzas/create - GET (shows form to create new pizza) ADMIN OK
                // /pizzas -> POST (creates new pizza) ADMIN OK
                // /pizzas/id/edit -> GET (shows form to modify pizza) ADMIN OK
                // /pizzas/id -> PUT (modifies a pizza) ADMIN OK
                .authorizeHttpRequests(auth -> auth

                        // ADMIN ONLY
                        .requestMatchers(HttpMethod.GET, "/pizzas/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/offers/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pizzas/*/edit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/offers/*/edit").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/ingredients").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/pizzas").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/pizzas/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pizzas/*").hasRole("ADMIN")

                        // ADMIN + USER
                        .requestMatchers(HttpMethod.GET, "/pizzas").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pizzas/*").hasAnyRole("USER", "ADMIN")

                        // ANY OTHER REQUEST

                        .anyRequest().authenticated())

                .formLogin(form -> form.permitAll())

                .logout(logout -> logout.permitAll());

        return http.build();

    }

}
