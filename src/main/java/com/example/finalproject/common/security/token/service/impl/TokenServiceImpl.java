package com.example.finalproject.common.security.token.service.impl;


import com.example.finalproject.common.security.token.core.exception.TokenNotFoundException;
import com.example.finalproject.common.security.token.entity.JWTToken;
import com.example.finalproject.common.security.token.repository.TokenRepository;
import com.example.finalproject.common.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;



import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public JWTToken getToken(String value) throws TokenNotFoundException {
        if (StringUtils.isEmpty(value)) {
            throw new TokenNotFoundException();
        }
        Optional<JWTToken> token = tokenRepository.findByToken(value);
        if (token.isEmpty()) {
            throw new TokenNotFoundException();
        }
        return token.get();
    }

    @Override
    public JWTToken save(JWTToken JWTToken) {
        return tokenRepository.save(JWTToken);
    }

    @Override
    public void delete(String token) throws TokenNotFoundException {
        Optional<JWTToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            throw new TokenNotFoundException();
        }

        tokenRepository.delete(optionalToken.get());
    }
}
