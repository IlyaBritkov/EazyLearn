package com.eazylearn.repository;

import com.eazylearn.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @NotNull
    @Override
    List<User> findAll();

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
