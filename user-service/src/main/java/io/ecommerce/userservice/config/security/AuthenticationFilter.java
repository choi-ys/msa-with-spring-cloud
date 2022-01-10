package io.ecommerce.userservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ecommerce.userservice.core.domain.dto.request.LoginRequest;
import io.ecommerce.userservice.core.service.UserQueryService;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : choi-ys
 * @date : 2022/01/10 5:26 오후
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserQueryService userQueryService;
    private Environment environment;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            UserQueryService userQueryService,
            Environment environment
    ) {
        super(authenticationManager);
        this.userQueryService = userQueryService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        // TODO issue JWT Token and set response header
    }
}
