package com.example.strongteamtesttask.config;

import com.example.strongteamtesttask.jwt.JwtAuthFilter;
import com.example.strongteamtesttask.jwt.JwtUserDetailsService;
import com.example.strongteamtesttask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final String LOGIN_ENDPOINT = "/api/auth/**";
    private static final String NEWS_ENDPOINT = "/api/news/**";
    private static final String NEWS_SOURCES_ENDPOINT = "/api/news-sources/**";
    private static final String TOPICS_ENDPOINT = "/api/topics/**";
    private static final String STATISTICS_ENDPOINT = "/api/statistics/**";

    private final JwtAuthFilter authFilter;

    public SecurityConfig(JwtAuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(@Autowired UserRepository userRepository){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService(userRepository));
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(@Autowired UserRepository userRepository) {
        return new JwtUserDetailsService(userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, @Autowired UserRepository userRepository) throws Exception {
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
                .requestMatchers(STATISTICS_ENDPOINT).authenticated()
                .and()
                .authenticationProvider(authenticationProvider(userRepository))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
