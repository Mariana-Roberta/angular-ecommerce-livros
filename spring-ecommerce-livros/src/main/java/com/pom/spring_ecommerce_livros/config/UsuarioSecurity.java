package com.pom.spring_ecommerce_livros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import com.pom.spring_ecommerce_livros.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class UsuarioSecurity {

    private UsuarioService usuarioService;

    public UsuarioSecurity(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/**").authenticated()
            )
            .formLogin((form) -> form
                .loginProcessingUrl("/api/login")
                .defaultSuccessUrl("/api/home", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/api/login?logout")
                .permitAll()
            )
            .exceptionHandling((exceptions) -> exceptions
                .accessDeniedPage("/api/access-denied")
            );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
        return auth.build();
    }
}
