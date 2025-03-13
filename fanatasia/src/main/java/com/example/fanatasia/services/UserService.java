package com.example.fanatasia.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.fanatasia.models.User;
import com.example.fanatasia.models.enums.Role;
import com.example.fanatasia.repositoryes.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Recupera un custode in base al suo ID.
     * 
     * @param id l'id del custode
     * @return il custode trovato
     * @throws RuntimeException se il custode non viene trovato
     */
    public User findCustodeById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Custode non trovata con id: " + id));
    }

    /**
     * Restituisce la lista delle creature disponibili.
     * 
     * @return lista delle creature
     */
    public List<User> findAllCustodi() {
        return userRepository.findByRole(Role.CUSTODE);
    }
}
