package com.example.finalproject.common.security.user;

import com.example.finalproject.common.entity.User;
import com.example.finalproject.common.repository.UserRepository;
import com.example.finalproject.customer.core.exception.NotFoundIdentityNumberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identity)  {
        Optional<User> user = userRepository.findByUserNumber(identity);
        if (user.isEmpty()) {
            try {
                throw new NotFoundIdentityNumberException();
            } catch (NotFoundIdentityNumberException e) {
                throw new RuntimeException(e);
            }
        }
        return new CustomUserDetail(user.get());
    }


}
