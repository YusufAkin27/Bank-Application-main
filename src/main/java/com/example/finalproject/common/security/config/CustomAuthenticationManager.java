package com.example.finalproject.common.security.config;

import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.common.security.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetail userDetail = (CustomUserDetail) userDetailsService.loadUserByUsername(username);

        if (userDetail != null && passwordEncoder.matches(password, userDetail.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        } else {
            throw new AuthenticationException("Kullanıcı adı veya parola hatalı") {};
        }
    }
}
