package com.example.finalproject.customer.business.concretes;

import com.example.finalproject.common.security.jwt.impl.JWTHelper;
import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.customer.business.abstarcts.AuthService;
import com.example.finalproject.customer.core.constant.Utils;
import com.example.finalproject.customer.core.exception.LoginFailedException;
import com.example.finalproject.customer.core.response.LoginResponse;
import com.example.finalproject.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthManager implements AuthService {


    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;

    @Override
    public ServiceResponse login(String userNumber, String password) throws LoginFailedException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userNumber, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            List<String> roles = Utils.SimpleGrantedAuthorityToListString((Collection<GrantedAuthority>) customUserDetail.getAuthorities());
            String token = jwtHelper.generate(userNumber, roles);
            customUserDetail.setToken(token);
            LoginResponse loginResponse = new LoginResponse(token);
            return new ServiceResponse(loginResponse.getToken(),true);
        } catch (Exception e) {
            throw new LoginFailedException();
        }
    }
}
