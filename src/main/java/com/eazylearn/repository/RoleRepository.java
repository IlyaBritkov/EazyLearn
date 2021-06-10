package com.eazylearn.repository;

import com.eazylearn.entity.Role;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    @Override
    @NotNull
    List<Role> findAll();

    Optional<Role> findRoleByName(String name);
}
