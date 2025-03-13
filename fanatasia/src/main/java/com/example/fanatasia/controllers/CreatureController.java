package com.example.fanatasia.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fanatasia.models.Creature;
import com.example.fanatasia.models.User;
import com.example.fanatasia.services.CreatureService;
import com.example.fanatasia.services.UserService;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/creature")
@RequiredArgsConstructor
public class CreatureController {

    private final CreatureService creatureService;
    private final UserService userService;

    @GetMapping
    public String listCreature(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Recupera il custode in base all'email dell'utente loggato
        User custode = userService.findByUser(userDetails.getUsername());
        List<Creature> creatures = creatureService.getAllCreatures(custode);
        model.addAttribute("creatures", creatures);
        return "creature";
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<String> addCreature(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        // Recupera il custode in base all'email dell'utente loggato
        User custode = userService.findByUser(userDetails.getUsername());

        if (custode == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato");
        }

        // Crea una nuova creatura
        Creature creature = new Creature();
        creature.setName(creature.getName());
        creature.setSpecies(creature.getSpecies());
        creature.setDangerLevel(creature.getDangerLevel());
        creature.setCustode(custode); // Assegna la creatura al custode

        creatureService.addCreature(creature);

        return ResponseEntity.ok("Creatura aggiunta con successo");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateCreature(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id, @RequestBody Creature creature) {
        // Recupera il custode in base all'email dell'utente loggato
        User custode = userService.findByUser(userDetails.getUsername());

        if (custode == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato");
        }

        // Recupera la creatura da aggiornare
        Optional<Creature> creaturePippo = creatureService.getCreatureById(id);
        Creature creatureToUpdate = creaturePippo.get();

        if (creatureToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Creatura non trovata");
        }

        // Aggiorna i dati della creatura
        creatureToUpdate.setName(creature.getName());
        creatureToUpdate.setSpecies(creature.getSpecies());
        creatureToUpdate.setDangerLevel(creature.getDangerLevel());

        creatureService.addCreature(creatureToUpdate);

        return ResponseEntity.ok("Creatura aggiornata con successo");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCreature(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        // Recupera il custode in base all'email dell'utente loggato
        User custode = userService.findByUser(userDetails.getUsername());

        if (custode == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato");
        }

        // Cerca la creatura nel database
        Optional<Creature> optionalCreature = creatureService.findById(id);

        if (optionalCreature.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Creatura non trovata");
        }

        Creature creature = optionalCreature.get();
        // Verifica se la creatura appartiene al custode autenticato

        if (!creature.getCustode().getId().equals(custode.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non puoi eliminare questa creatura");
        }

        // Elimina la creatura
        creatureService.deleteCreature(id);

        return ResponseEntity.ok("Creatura eliminata con successo");
    }

    @PatchMapping("/updateDangerLevel/{id}")
    public ResponseEntity<String> updateDangerLevel(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestParam int dangerLevel) {

        // Recupera il custode autenticato
        User custode = userService.findByUser(userDetails.getUsername());

        if (custode == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autorizzato");
        }

        // Cerca la creatura nel database
        Optional<Creature> optionalCreature = creatureService.findById(id);

        if (optionalCreature.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Creatura non trovata");
        }

        Creature creature = optionalCreature.get();

        // Verifica se la creatura appartiene al custode autenticato
        if (!creature.getCustode().getId().equals(custode.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non puoi modificare questa creatura");
        }

        // Modifica solo il livello di pericolo (dangerLevel)
        creature.setDangerLevel(dangerLevel);

        // Salva la creatura aggiornata
        creatureService.addCreature(creature);

        return ResponseEntity.ok("Livello di pericolo aggiornato con successo!");
    }

}