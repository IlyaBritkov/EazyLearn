package com.eazylearn.service;

import com.eazylearn.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAllRoles();

    Optional<Role> findRoleByName(String roleName);
}
