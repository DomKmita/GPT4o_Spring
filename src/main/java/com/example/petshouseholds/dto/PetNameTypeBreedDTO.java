package com.example.petshouseholds.dto;
import jakarta.validation.constraints.NotBlank;

public record PetNameTypeBreedDTO(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Animal type cannot be blank")
        String animalType,

        @NotBlank(message = "Breed cannot be blank")
        String breed
) {}
