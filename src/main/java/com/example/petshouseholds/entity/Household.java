package com.example.petshouseholds.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "households")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Household {

    @Id
    private String eircode;

    @Column(name = "number_of_occupants", nullable = false)
    private int numberOfOccupants;

    @Column(name = "max_number_of_occupants", nullable = false)
    private int maxNumberOfOccupants;

    @Column(name = "owner_occupied", nullable = false)
    private boolean ownerOccupied;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();
}
