package com.example.strongteamtesttask.config;

import com.example.strongteamtesttask.security.JwtConfiguration;
import com.example.strongteamtesttask.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String LOGIN_ENDPOINT = "/api/auth/**";
    private static final String NEWS_ENDPOINT = "/api/news/**";
    private static final String NEWS_SOURCES_ENDPOINT = "/api/news-sources/**";
    private static final String TOPICS_ENDPOINT = "/api/topics/**";

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(LOGIN_ENDPOINT).permitAll()
                .requestMatchers(NEWS_ENDPOINT).authenticated()
                .requestMatchers(NEWS_SOURCES_ENDPOINT).authenticated()
                .requestMatchers(TOPICS_ENDPOINT).authenticated()
                .and()
                .apply(new JwtConfiguration(jwtTokenProvider));
        return http.build();
    }

}
