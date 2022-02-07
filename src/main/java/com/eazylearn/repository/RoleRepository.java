package com.eazylearn.repository;

import com.eazylearn.entity.Role;
import com.eazylearn.enums.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

    Optional<Role> findByName(UserRole name);
}
