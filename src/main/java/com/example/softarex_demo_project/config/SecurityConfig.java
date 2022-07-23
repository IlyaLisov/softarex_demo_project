package com.example.softarex_demo_project.config;

import com.example.softarex_demo_project.security.jwt.JwtConfigurer;
import com.example.softarex_demo_project.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.softarex_demo_project.rest.authentication.AuthenticationRestUrls.BASE_URL;
import static com.example.softarex_demo_project.rest.authentication.AuthenticationRestUrls.LOGIN_ULR;
import static com.example.softarex_demo_project.rest.authentication.AuthenticationRestUrls.REFRESH_URL;
import static com.example.softarex_demo_project.rest.authentication.AuthenticationRestUrls.REGISTER_URL;

/**
 * This class is an application`s config class.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_ENDPOINT = BASE_URL + LOGIN_ULR;
    private static final String REGISTER_ENDPOINT = BASE_URL + REGISTER_URL;
    private static final String REFRESH_ENDPOINT = BASE_URL + REFRESH_URL;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT, REFRESH_ENDPOINT).permitAll()
                .antMatchers(REGISTER_ENDPOINT).not().authenticated()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .rememberMe();

        return http.build();
    }
}
