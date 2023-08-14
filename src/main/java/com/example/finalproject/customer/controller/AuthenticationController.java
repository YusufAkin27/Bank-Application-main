package com.example.finalproject.customer.controller;



import com.example.finalproject.common.security.jwt.impl.JWTHelper;
import com.example.finalproject.common.security.token.core.exception.TokenNotFoundException;
import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.customer.business.abstarcts.AuthService;
import com.example.finalproject.customer.core.exception.LoginFailedException;
import com.example.finalproject.customer.core.exception.NotFoundIdentityNumberException;
import com.example.finalproject.customer.core.request.LoginFormRequest;
import com.example.finalproject.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/bank/authentication")
@Validated
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;
    private final JWTHelper jwtHelper;

    @PostMapping("/login")
    public ServiceResponse login(@RequestBody @Valid LoginFormRequest loginFormRequest) throws LoginFailedException, NotFoundIdentityNumberException {
        return authService.login(loginFormRequest.getUserNumber(), loginFormRequest.getPassword());
    }

    @PostMapping("/logout")
    public ServiceResponse logout(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail) throws TokenNotFoundException {
        jwtHelper.deleteTokenForLogout(userDetail.getToken());
        return new ServiceResponse("Çıkış işlemi başarılı",true);
    }


}
