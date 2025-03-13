package com.example.fanatasia.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fanatasia.models.Creature;
import com.example.fanatasia.models.User;
import com.example.fanatasia.repositoryes.CreatureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatureService {
    
    private final CreatureRepository creatureRepository;

    public List<Creature> getAllCreatures(User custode) {
        return creatureRepository.findByCustode(custode);
    }
}
