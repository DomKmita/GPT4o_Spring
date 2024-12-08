package com.example.petshouseholds.controller;

import com.example.petshouseholds.dto.HouseholdDTO;
import com.example.petshouseholds.dto.HouseholdWithoutPetsDTO;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.service.HouseholdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
@RequiredArgsConstructor
public class HouseholdController {

    private final HouseholdService householdService;

    // Create a new household
   // @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Household> createHousehold(@Valid @RequestBody HouseholdDTO householdDTO) {
        Household household = new Household(
                householdDTO.eircode(),
                householdDTO.numberOfOccupants(),
                householdDTO.maxNumberOfOccupants(),
                householdDTO.ownerOccupied(),
                null
        );
        return ResponseEntity.ok(householdService.createHousehold(household));
    }

    // Get all households
    @GetMapping
    public ResponseEntity<List<Household>> getAllHouseholds() {
        return ResponseEntity.ok(householdService.getAllHouseholds());
    }

    // Get household by eircode without pets
    @GetMapping("/{eircode}")
    public ResponseEntity<HouseholdWithoutPetsDTO> getHouseholdByEircodeNoPets(@PathVariable String eircode) {
        return ResponseEntity.ok(householdService.getHouseholdByEircodeNoPets(eircode));
    }

    // Get household by eircode with pets
    @GetMapping("/{eircode}/with-pets")
    public ResponseEntity<Household> getHouseholdByEircodeWithPets(@PathVariable String eircode) {
        return ResponseEntity.ok(householdService.getHouseholdByEircodeWithPets(eircode));
    }

    // Update household
   // @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{eircode}")
    public ResponseEntity<Household> updateHousehold(@PathVariable String eircode, @Valid @RequestBody HouseholdDTO householdDTO) {
        Household updatedHousehold = new Household(
                eircode,
                householdDTO.numberOfOccupants(),
                householdDTO.maxNumberOfOccupants(),
                householdDTO.ownerOccupied(),
                null
        );
        return ResponseEntity.ok(householdService.updateHousehold(eircode, updatedHousehold));
    }

    // Delete household by eircode
   // @Secured("ROLE_ADMIN")
    @DeleteMapping("/{eircode}")
    public ResponseEntity<Void> deleteHouseholdByEircode(@PathVariable String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return ResponseEntity.noContent().build();
    }

    // Get households with no pets
    @GetMapping("/no-pets")
    public ResponseEntity<List<Household>> findHouseholdsWithNoPets() {
        return ResponseEntity.ok(householdService.findHouseholdsWithNoPets());
    }

    // Get owner-occupied households
    @GetMapping("/owner-occupied")
    public ResponseEntity<List<Household>> findOwnerOccupiedHouseholds() {
        return ResponseEntity.ok(householdService.findOwnerOccupiedHouseholds());
    }

    // Get household statistics
    @GetMapping("/statistics")
    public ResponseEntity<Object> getHouseholdStatistics() {
        return ResponseEntity.ok(householdService.getHouseholdStatistics());
    }
}
