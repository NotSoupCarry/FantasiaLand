package com.example.fanatasia.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.fanatasia.models.User;
import com.example.fanatasia.models.enums.Role;
import com.example.fanatasia.repositoryes.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        if (user.getRole() == null || user.getRole().name() == null) {
            user.setRole(Role.ROLE_CUSTODE);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User findCustodeById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Custode non trovata con id: " + id));
    }

    public List<User> findAllCustodi() {
        return userRepository.findByRole(Role.ROLE_CUSTODE);
    }

}
