package com.example.homework_28.Config;

import com.example.homework_28.Service.MyUserDetailsService;
import com.example.homework_28.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers( "/api/v1/users/getuser","/api/v1/users/register","/api/v1/product/update/**","/api/v1/product","/api/v1/product/add","/api/v1/product/delete/**","/api/v1/product/getproduct","/api/v1/orders/delete/**","/api/v1/product/get/product/**","/api/v1/users/update","/api/v1/users/get/users/**").permitAll()
                .requestMatchers("/api/v1/users/update","/api/v1/product/add" ,"/api/v1/users/get/users/**", "/api/v1/users/delete/**",
                        "api/v1/product","/api/v1/product/update/**","/api/v1/product/delete/**","/api/v1/product/getproduct","/api/v1/orders/Status/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/delete/**","/api/v1/product/add","/api/v1/product/delete/**","/api/v1/product/getproduct","/api/v1/orders","/api/v1/orders/add/**","/api/v1/orders/get/orders","/api/v1/orders/update/**","/api/v1/orders/get/**").hasAuthority("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
