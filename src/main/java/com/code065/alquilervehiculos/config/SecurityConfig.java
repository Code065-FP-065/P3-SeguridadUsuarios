package com.code065.alquilervehiculos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Recursos públicos de la aplicación.
                        .requestMatchers(
                                "/",
                                "/login",
                                "/registro",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "webjars/**"
                        ).permitAll()
                        // Rutas exclusivas para usuarios con rol ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Rutas accesibles para usuarios con rol USER o ADMIN.
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        // Cualquier otra petición requiere autenticación.
                        .anyRequest().authenticated()
                )
                // Configuración del formulario de login.
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                // Configuración del cierre de sesión.
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
