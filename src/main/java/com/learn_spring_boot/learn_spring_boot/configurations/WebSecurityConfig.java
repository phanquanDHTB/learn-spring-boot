package com.learn_spring_boot.learn_spring_boot.configurations;

import com.learn_spring_boot.learn_spring_boot.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("api/v1/user/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "api/v1/categories/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "api/v1/categories/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.POST, "api/v1/categories").hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, "api/v1/categories/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.GET, "api/v1/products/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "api/v1/products/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.POST, "api/v1/products").hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, "api/v1/products/**").hasAnyRole(Role.ADMIN)

                            .requestMatchers(HttpMethod.GET, "api/v1/order/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "api/v1/order/**").hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.POST, "api/v1/order").permitAll()

                            .requestMatchers(HttpMethod.GET, "api/v1/orderdetail/**").permitAll()

                            .requestMatchers(HttpMethod.POST, "api/v1/upload").hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, "api/v1/upload/images/**").permitAll()
                    ;
                })
                .csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return httpSecurity.build();
    }
}
