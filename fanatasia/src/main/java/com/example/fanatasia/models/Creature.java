package com.example.fanatasia.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "creatures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Creature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String species;

    private int dangerLevel; // Da 1 a 10

    @ManyToOne
    @JoinColumn(name = "custode_id")
    private User custode;
}
