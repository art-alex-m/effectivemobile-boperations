package ru.effectivemobile.boperations.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@OpenAPIDefinition(
        info = @Info(title = "EffectiveMobile Bank Operations API", version = "1.0.0"),
        servers = @Server(url = "http://localhost:6060/boperations-webapp", description = "Local")
)
@SecuritySchemes(value = {
        @SecurityScheme(
                name = "JwtToken",
                paramName = "Authorization",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                scheme = "JWT"
        )
})
@Configuration
public class OpenapiConfiguration {

    @Bean
    @Order(2000)
    public SecurityFilterChain openApiSecurityFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/swagger-ui/**", "/v3/api-docs/**")
                .authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll())
                .build();
    }
}
