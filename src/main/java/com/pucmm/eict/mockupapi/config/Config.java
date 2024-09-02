package com.pucmm.eict.mockupapi.config;

import com.pucmm.eict.mockupapi.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/error/**").permitAll()
                                .requestMatchers("/manage/**").hasRole(String.valueOf(UserRole.ADMINISTRADOR))
                                .requestMatchers("assets/**", "css/**", "js/**").permitAll()
                                .requestMatchers("/{hash}/api/**").permitAll()
                                .requestMatchers("/authenticateToken").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login
                                .loginPage("/login")
                                .permitAll()
                                .defaultSuccessUrl("/projects")
                                .usernameParameter("username")
                                .passwordParameter("password")
                )
                .logout(logout ->
                        logout
                                .permitAll()
                                .logoutUrl("/logout")
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    request.getSession().invalidate();
                                    response.sendRedirect("/login");
                                })
                )
                .build();
    }
}
