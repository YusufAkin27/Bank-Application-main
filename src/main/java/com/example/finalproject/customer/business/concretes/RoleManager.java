package com.example.finalproject.customer.business.concretes;

import com.example.finalproject.customer.business.abstarcts.RoleService;
import com.example.finalproject.customer.core.constant.CustomerConstant;
import com.example.finalproject.customer.entity.Role;
import com.example.finalproject.customer.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleManager implements RoleService {

    private final RoleRepository repository;


    @Override
    public Role getRoleByName(String role) {
        return repository.findByName(role).get();

    }

    @PostConstruct
    public void addRoleDatabase() {
        Optional<Role> optionalRole = repository.findByName(CustomerConstant.ROLE_USER);
        if (optionalRole.isEmpty()) {
            Role roleUser = new Role(CustomerConstant.ROLE_USER);
            Role roleAdmin = new Role(CustomerConstant.ROLE_ADMIN);
            repository.saveAll(List.of(roleAdmin, roleUser));
        }
    }

}

