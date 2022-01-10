package io.ecommerce.userservice.config.security;

import io.ecommerce.userservice.core.service.UserQueryService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
                .authorizeRequests()
                .antMatchers("/**")
                .hasIpAddress("192.168.1.57")
                .and()
                .addFilter(getAuthenticationFilter())
        ;
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(), userQueryService, environment);

        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userQueryService).passwordEncoder(passwordEncoder);
    }
}
