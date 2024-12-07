package com.example.petshouseholds.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdDTO {
    private String eircode;
    private int numberOfOccupants;
    private int maxNumberOfOccupants;
    private boolean ownerOccupied;
}
