package com.example.fanatasia.repositoryes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fanatasia.models.User;
import com.example.fanatasia.models.enums.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByRole(Role role);
}
