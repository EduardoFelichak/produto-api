package com.api.app.services;

import com.api.app.models.UserModel;
import com.api.app.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<UserModel> signIn(String email, String password) {
        Optional<UserModel> user = userRepository.findByEmail(email);

        if (user.isPresent() && Objects.equals(user.get().getPass(), password)) {
            return user;
        }

        return Optional.empty();
    }

    public Optional<UserModel> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
