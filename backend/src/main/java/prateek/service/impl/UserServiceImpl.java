package prateek.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prateek.entity.User;
import prateek.repository.UserRepository;
import prateek.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        log.info("Saving user with email {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByPhoneHash(String phoneHash) {
        return userRepository.findByPhoneHash(phoneHash);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
