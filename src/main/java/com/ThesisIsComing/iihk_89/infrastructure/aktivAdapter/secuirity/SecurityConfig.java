package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.secuirity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration// -> lies beim start alle @bean methode werden regestiert
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder
                .authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/", "/css/**", "/public/**").permitAll()
                        .requestMatchers("/betreuer-profil/**").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/betreuer/**").hasRole("BETREUER")
                .requestMatchers("/matching").authenticated()
                .requestMatchers("/datei/**").authenticated()
                        .requestMatchers("/thema/**").authenticated()
                        .requestMatchers("/alle-betreuer").authenticated()
                        .requestMatchers("/alle-themen").authenticated()
                        .requestMatchers("/datei/**").authenticated()
                        .anyRequest().authenticated())
                .oauth2Login(config -> config
                        .userInfoEndpoint(info ->
                                info.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/", true))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED));
        return chainBuilder.build();
    }
}
