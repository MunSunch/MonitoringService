package com.munsun.monitoring_service.backend.security.configurations;

import com.munsun.monitoring_service.backend.security.JwtFilter;
import com.munsun.monitoring_service.backend.security.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private HandlerMappingIntrospector introspector;

    @Bean
    public MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(mvc(introspector).pattern("/login"),
                                                 mvc(introspector).pattern("/register")).permitAll()
                                .requestMatchers(mvc(introspector).pattern("/add/*"),
                                                 mvc(introspector).pattern("/get/all/actual"),
                                                 mvc(introspector).pattern("/get/all"),
                                                 mvc(introspector).pattern("/get/all/*")).hasAuthority("allRead")
                                .requestMatchers(mvc(introspector).pattern("/get"),
                                                 mvc(introspector).pattern("/get/actual"),
                                                 mvc(introspector).pattern("/get/*")).hasAuthority("read")
                                .requestMatchers(mvc(introspector).pattern("/save")).hasAuthority("write")
                                .anyRequest().authenticated())
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
