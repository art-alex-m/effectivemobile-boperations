package ru.effectivemobile.boperations.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.compression.GzipCompressionAlgorithm;
import io.jsonwebtoken.io.CompressionAlgorithm;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import ru.effectivemobile.boperations.service.AppAuthHeaderAuthorizationProvider;

import java.security.KeyPair;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
public class AppSecurityConfiguration {
    @Bean
    @Order(100)
    public SecurityFilterChain baseSecurityFilter(HttpSecurity httpSecurity,
            RequestHeaderAuthenticationFilter authTokenAuthenticationFilter,
            AuthenticationManager authenticationManager) throws Exception {

        return httpSecurity
                .securityMatcher(antMatcher("/api/**"))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(authTokenAuthenticationFilter)
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers(antMatcher("/api/login/**"),
                                antMatcher(HttpMethod.POST, "/api/users")).permitAll()
                        .anyRequest().authenticated())
                .build();
    }

    @Bean
    @Order
    public SecurityFilterChain denySecurityFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(customizer -> customizer.anyRequest().denyAll()).build();
    }

    @Bean
    public KeyPair secretJwtKey() {
        return Jwts.SIG.ES256.keyPair().build();
    }

    @Bean
    public CompressionAlgorithm jwtCompressAlgorithm() {
        return new GzipCompressionAlgorithm();
    }

    @Bean
    public RequestHeaderAuthenticationFilter authTokenAuthenticationFilter(
            AuthenticationManager authenticationManager) {
        RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
        filter.setPrincipalRequestHeader("Authorization");
        filter.setExceptionIfHeaderMissing(false);
        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    @Bean
    public FilterRegistrationBean<RequestHeaderAuthenticationFilter> excludeFromServletAuthTokenAuthenticationFilter(
            RequestHeaderAuthenticationFilter authTokenAuthenticationFilter) {
        FilterRegistrationBean<RequestHeaderAuthenticationFilter> registration =
                new FilterRegistrationBean<>(authTokenAuthenticationFilter);
        registration.setEnabled(false);

        return registration;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
            AppAuthHeaderAuthorizationProvider appAuthHeaderAuthorizationProvider) throws Exception {

        AuthenticationManagerBuilder builder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(appAuthHeaderAuthorizationProvider);

        return builder.build();
    }
}
