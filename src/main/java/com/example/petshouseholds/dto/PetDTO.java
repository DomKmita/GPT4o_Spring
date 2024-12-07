package com.example.petshouseholds.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {
    private Long id;
    private String name;
    private String animalType;
    private String breed;
    private int age;
    private String eircode;
}
