package com.ApiRestMiBus.config;

import com.ApiRestMiBus.model.service.UserDetailServiceImpl;
import com.ApiRestMiBus.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/roles/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/roles/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/roles/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/roles/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/flotas/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/flotas/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/flotas/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/flotas/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/empresas/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/empresas/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/empresas/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/empresas/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/vehiculos/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/vehiculos/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/vehiculos/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/vehiculos/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/gps/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/gps/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/gps/**").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/gps/**").hasAnyRole("ADMIN");
                    http.requestMatchers("/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html").permitAll();
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200","http://localhost:5173","http://137.184.227.202")); //
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }
}
