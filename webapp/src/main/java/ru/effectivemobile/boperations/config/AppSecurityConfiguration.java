package ru.effectivemobile.boperations.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
public class AppSecurityConfiguration {
    @Bean
    @Order(100)
    public SecurityFilterChain baseSecurityFilter(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .securityMatcher(antMatcher("/api/**"))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll())
                .build();
    }

    @Bean
    @Order
    public SecurityFilterChain denySecurityFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(customizer -> customizer.anyRequest().denyAll()).build();
    }
}
