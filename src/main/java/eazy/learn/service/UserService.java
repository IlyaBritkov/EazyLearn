package eazy.learn.service;

import eazy.learn.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String login);

    Optional<User> findUserEntityByEmail(String email);

    User createUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);
}
