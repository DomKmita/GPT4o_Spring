package com.example.petshouseholds.dto;

public record HouseholdWithoutPetsDTO(
        String eircode,
        int numberOfOccupants,
        int maxNumberOfOccupants,
        boolean ownerOccupied
) {}
