package io.ecommerce.userservice.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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
    ) {
        User user = (User) authResult.getPrincipal();
        response.setHeader("token", tokenBuilder(user));
        // TODO issue JWT Token and set response header
    }

    private String tokenBuilder(User user) {
        return JWT.create()
                .withIssuer("ISSUER")
                .withSubject("SUBJECT")
                .withAudience("AUDIENCE")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(
                        new Date(System.currentTimeMillis()
                                + Long.parseLong(environment.getProperty("token.token-validity-in-seconds-term"))
                                * 1000)
                )
                .withClaim("TYPE", "ACCESS_TOKEN")
                .withClaim("token", user.getUsername())
                .sign(Algorithm.HMAC512(environment.getProperty("token.secret-key")))
                ;
    }
}
