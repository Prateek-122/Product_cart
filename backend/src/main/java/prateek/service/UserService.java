package prateek.service;

import prateek.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    void delete(Long id);
}
