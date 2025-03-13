package com.example.fanatasia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.fanatasia.models.Creature;
import com.example.fanatasia.models.User;
import com.example.fanatasia.repositoryes.CreatureRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatureService {

    private final CreatureRepository creatureRepository;

    public List<Creature> getAllCreatures(User custode) {
        return creatureRepository.findByCustode(custode);
    }

    public Creature addCreature(Creature creature) {
        return creatureRepository.save(creature);
    }

    public Optional<Creature> getCreatureById(Long id) {
        return creatureRepository.findById(id);
    }

    public void deleteCreature(Long id) {
        creatureRepository.findById(id).ifPresentOrElse(
                creatureRepository::delete,
                () -> {
                    throw new RuntimeException("Creature not found");
                });
    }

    public Optional<Creature> findById(Long id) {
        return creatureRepository.findById(id);
    }

    /**
     * Aggiunge una nuova creatura.
     * 
     * @param Creature creatura da aggiungere
     * @return la creatura trovata
     */
    @Transactional
    public Creature saveCreatura(@Valid Creature creatura) {
        return creatureRepository.save(creatura);
    }

    /**
     * Recupera una creatura in base al suo ID.
     * 
     * @param id l'id della creatura
     * @return la creatura trovata
     * @throws RuntimeException se la creatura non viene trovata
     */
    public Creature findCreatureById(Long id) {
        return creatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Creatura non trovata con id: " + id));
    }

    /**
     * Restituisce la lista delle creature disponibili.
     * 
     * @return lista delle creature
     */
    public List<Creature> findAllCreature() {
        return creatureRepository.findAll();
    }

}
