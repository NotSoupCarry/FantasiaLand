package com.example.fanatasia.repositoryes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fanatasia.models.Creature;
import com.example.fanatasia.models.User;

public interface CreatureRepository extends JpaRepository<Creature, Long> {
    List<Creature> findByCustode(User custode);
}