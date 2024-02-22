package com.ekart.userservice.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ekart.userservice.entity.Role;
import com.ekart.userservice.entity.RoleName;
import com.ekart.userservice.repository.RoleRepository;
import com.ekart.userservice.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }

}