package com.example.petshouseholds.dto;
import jakarta.validation.constraints.*;

public record PetDTO(
        Long id,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Animal type cannot be blank")
        String animalType,

        String breed,

        @Min(value = 0, message = "Age cannot be negative")
        int age,

        @NotBlank(message = "Eircode cannot be blank")
        String eircode
) {}
