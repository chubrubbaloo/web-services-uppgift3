package me.code.uppgift3projekt.security;

import me.code.uppgift3projekt.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    JwtTokenService jwtTokenService;

    @Autowired
    public JwtFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String authorizationHeader = httpRequest.getHeader("authorization");
        if (!jwtTokenService.validate(authorizationHeader.split(" ")[1])){
            httpResponse.setStatus(401);
            return;
        }
        httpRequest.setAttribute("user", jwtTokenService.getUserFromToken(authorizationHeader.split(" ")[1]) );
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
