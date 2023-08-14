package com.example.finalproject.common.security.config;

import com.example.finalproject.common.security.filter.CustomAuthenticationFilter;

import com.example.finalproject.customer.core.constant.CustomerConstant;
import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST_FOR_SWAGGER = {
            // swagger 3 open api
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };
    private static final String[] AUTH_WHITELIST = {
            "/bank/customers/sing-up/**",
            "/bank/authentication/login/**",
            "/bank/customers/createAdmin/**",
            "/bank/atm/**",
    };
    private static final String[] AUTH_WHITELIST_FOR_USER = {
            "/bank/customers/**",
            "/bank/accounts/**",
            "/bank/cards/**"
    };

    private final CustomAuthenticationFilter authenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST_FOR_SWAGGER).permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(AUTH_WHITELIST_FOR_USER).hasAnyAuthority(CustomerConstant.ROLE_USER)
                .anyRequest().authenticated();
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

}
