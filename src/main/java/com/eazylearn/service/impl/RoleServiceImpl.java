package com.eazylearn.service.impl;

import com.eazylearn.entity.Role;
import com.eazylearn.repository.RoleRepository;
import com.eazylearn.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor(onConstructor_ = @Autowired)

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName.toUpperCase());
    }
}
