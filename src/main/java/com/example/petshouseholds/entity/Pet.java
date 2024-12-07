package com.example.petshouseholds.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "animal_type", nullable = false)
    private String animalType;

    private String breed;

    @Column(nullable = false)
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eircode", nullable = false)
    private Household household;
}
