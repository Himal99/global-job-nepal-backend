package com.globaljobsnepal.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globaljobsnepal.auth.config.handlers.AppAuthenticationEntryPoint;
import com.globaljobsnepal.auth.enums.Roles;
import com.globaljobsnepal.auth.filter.SecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    // Whitelisted endpoints (no auth required)
    private static final String[] WHITE_LIST_API = {
            "/api/v1/auth/login",
            "/api/v1/auth/login/api-users",
            "/api/v1/auth/register",
            "/",
            "/api/v1/compress/callback",
            "/api/v1/compress/realtime-server-logs",
            "/api/v1/realtime/**",
            "/api/v1/compress-files",
            "/api/v1/download/**"
    };

    /**
     * Custom authentication entry point for unauthorized requests
     */
    @Bean
    public AppAuthenticationEntryPoint entryPoint() {
        return new AppAuthenticationEntryPoint(new ObjectMapper());
    }

    /**
     * Register SecurityFilter as a bean
     * Do NOT use FilterRegistrationBean for this filter
     */
    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    /**
     * Security filter chain configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF since JWT is used
                .csrf(AbstractHttpConfigurer::disable)

                // Role-based access control
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITE_LIST_API).permitAll()
                        .requestMatchers("/api/v1/user-info/**").hasAuthority(Roles.ADMIN.getValue())
                        .requestMatchers("/api/v1/admin/**").hasAuthority(Roles.ADMIN.getValue())
                        .requestMatchers("/api/v1/editor/**").hasAnyAuthority(
                                Roles.ADMIN.getValue(), Roles.EDITOR.getValue())
                        .anyRequest().authenticated()
                )

                // Custom entry point for unauthorized requests
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint()))

                // Stateless session
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add JWT filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Global CORS configuration
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // allow cookies if needed
        config.addAllowedOriginPattern("*"); // allow all origins
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // run CORS first
        return bean;
    }
}
