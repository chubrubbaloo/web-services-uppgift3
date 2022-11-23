package me.code.uppgift3projekt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
/**
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception
    {
        return http.cors().disable()
                .authorizeRequests()
                .antMatchers("/register", "/login")
                .permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .build();
    }
 */

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> {
                    auth.antMatchers("/","register").permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
