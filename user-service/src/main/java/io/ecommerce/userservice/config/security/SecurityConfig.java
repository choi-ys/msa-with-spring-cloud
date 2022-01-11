package io.ecommerce.userservice.config.security;

import io.ecommerce.userservice.core.service.UserQueryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : choi-ys
 * @date : 2022/01/07 5:18 오후
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserQueryService userQueryService;
    private final Environment environment;
    private final PasswordEncoder passwordEncoder;

    @Value("${ip-address.gateway}")
    private String GATEWAY;

    public SecurityConfig(
            UserQueryService userQueryService,
            Environment environment,
            PasswordEncoder passwordEncoder
    ) {
        this.userQueryService = userQueryService;
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(request -> {
                            request.antMatchers(HttpMethod.POST, "/login").permitAll();
                            request.antMatchers(HttpMethod.POST, "/user").permitAll();
                            request.anyRequest().hasIpAddress(GATEWAY).and().addFilter(getAuthenticationFilter());
                        }
                );
    }

    private AuthenticationFilter getAuthenticationFilter() {
        AuthenticationFilter authenticationFilter = null;
        try {
            authenticationFilter = new AuthenticationFilter(authenticationManager(), userQueryService, environment);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userQueryService).passwordEncoder(passwordEncoder);
    }
}
