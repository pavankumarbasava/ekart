package com.ekart.userservice.service;

import java.util.Optional;

import com.ekart.userservice.entity.Role;
import com.ekart.userservice.entity.RoleName;

public interface RoleService {

    Optional<Role> findByName(RoleName name);
}

