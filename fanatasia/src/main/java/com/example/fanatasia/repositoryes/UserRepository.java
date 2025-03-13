package com.example.fanatasia.repositoryes;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fanatasia.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
