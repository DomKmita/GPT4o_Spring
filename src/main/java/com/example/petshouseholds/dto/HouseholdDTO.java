package com.example.petshouseholds.dto;
import jakarta.validation.constraints.*;

public record HouseholdDTO(
        @NotBlank(message = "Eircode cannot be blank")
        String eircode,

        @Min(value = 0, message = "Number of occupants cannot be negative")
        int numberOfOccupants,

        @Min(value = 1, message = "Maximum number of occupants must be at least 1")
        int maxNumberOfOccupants,

        boolean ownerOccupied
) {}
