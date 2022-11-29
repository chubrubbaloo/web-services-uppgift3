package me.code.uppgift3projekt.security;

import me.code.uppgift3projekt.service.JwtTokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    JwtTokenService jwtTokenService;

    public SecurityConfig(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http.cors().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth -> auth.antMatchers("/","register").permitAll())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> loggingFilter(){
        FilterRegistrationBean<JwtFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JwtFilter(jwtTokenService));
        registrationBean.addUrlPatterns("/posts");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}

