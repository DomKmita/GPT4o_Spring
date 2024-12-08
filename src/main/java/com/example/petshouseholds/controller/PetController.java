package com.example.petshouseholds.controller;

import com.example.petshouseholds.dto.PetDTO;
import com.example.petshouseholds.dto.PetNameTypeBreedDTO;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.service.HouseholdService;
import com.example.petshouseholds.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final HouseholdService householdService;

    // Create a new pet
   // @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody @Valid PetDTO petDTO) {
        Household household = householdService.getHouseholdByEircode(petDTO.eircode());
        Pet pet = new Pet(petDTO.id(), petDTO.name(), petDTO.animalType(), petDTO.breed(), petDTO.age(), household);
        return ResponseEntity.ok(petService.createPet(pet));
    }

    // Get all pets
    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    // Get pet by ID
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    // Update pet
   // @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @Valid @RequestBody PetDTO petDTO) {
        Pet updatedPet = new Pet(
                id,
                petDTO.name(),
                petDTO.animalType(),
                petDTO.breed(),
                petDTO.age(),
                null
        );
        return ResponseEntity.ok(petService.updatePet(id, updatedPet));
    }

    // Delete pet by ID
  //  @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    // Delete pets by name (case insensitive)
  //  @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deletePetsByName(@PathVariable String name) {
        petService.deletePetsByName(name);
        return ResponseEntity.noContent().build();
    }

    // Find pets by animal type
    @GetMapping("/type/{animalType}")
    public ResponseEntity<List<Pet>> findPetsByAnimalType(@PathVariable String animalType) {
        return ResponseEntity.ok(petService.findPetsByAnimalType(animalType));
    }

    // Find pets by breed
    @GetMapping("/breed/{breed}")
    public ResponseEntity<List<Pet>> findPetsByBreed(@PathVariable String breed) {
        return ResponseEntity.ok(petService.findPetsByBreed(breed));
    }

    // Get name, type, and breed
    @GetMapping("/names")
    public ResponseEntity<List<PetNameTypeBreedDTO>> getNameAndBreedOnly() {
        return ResponseEntity.ok(petService.getNameTypeAndBreedOnly());
    }

    // Get pet statistics
   // @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/statistics")
    public ResponseEntity<Object> getPetStatistics() {
        return ResponseEntity.ok(petService.getPetStatistics());
    }
}
