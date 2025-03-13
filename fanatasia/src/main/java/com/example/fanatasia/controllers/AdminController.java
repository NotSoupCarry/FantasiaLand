package com.example.fanatasia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.fanatasia.models.Creature;
import com.example.fanatasia.models.User;
import com.example.fanatasia.services.CreatureService;
import com.example.fanatasia.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CreatureService creatureService;
    private final UserService userService;

    // Metodo per mostrare tutte le creature
    @GetMapping("/creature")
    public String showAllCreature(Model model) {
        model.addAttribute("creature", creatureService.findAllCreature());
        return "creature";
    }

    // Metodo per mostrare tutti i custodi
    @GetMapping("/custodi")
    public String showAllCustodi(Model model) {
        model.addAttribute("custodi", userService.findAllCustodi());
        return "custodi";
    }

    // Metodo per assegnare un nuovo custode ad una creatura
    @PutMapping("/assegnazione")
    public String assegnareCreatura(@RequestParam Long idCreatura, @RequestParam Long idCustode, Model model) {

        try {
            // Trova la creatura nel database
            Creature creatura = creatureService.findCreatureById(idCreatura);

            // Trova il custode nel database
            User custode = userService.findCustodeById(idCustode);

            // Assegna il custode alla creatura
            creatura.setCustode(custode);

            // Salva la creatura aggiornata
            creatureService.saveCreatura(creatura);

            // Passa il messaggio di successo al template
            model.addAttribute("message", "Creatura assegnata con successo al custode!");

        } catch (EntityNotFoundException e) {
            // Passa il messaggio di errore al template
            model.addAttribute("error", e.getMessage());
        }

        return "assegnazione";
    }

    // Metodo per creare una nuova creatura
    @PostMapping("/creazione")
    public String addCreatura(@ModelAttribute Creature creatura) {
        creatureService.saveCreatura(creatura);
        return "redirect:/creature";
    }

}