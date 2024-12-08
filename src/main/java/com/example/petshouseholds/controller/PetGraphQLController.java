package com.example.petshouseholds.controller;

import com.example.petshouseholds.dto.PetDTO;
import com.example.petshouseholds.dto.PetNameTypeBreedDTO;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.service.HouseholdService;
import com.example.petshouseholds.service.PetService;
import com.example.petshouseholds.service.impl.HouseholdServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PetGraphQLController {

    private final PetService petService;
    private final HouseholdService householdService;

    @QueryMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @QueryMapping
    public Pet getPetById(@Argument Long id) {
        return petService.getPetById(id);
    }

    @QueryMapping
    public List<Pet> findPetsByAnimalType(@Argument String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    @QueryMapping
    public List<Pet> findPetsByBreed(@Argument String breed) {
        return petService.findPetsByBreed(breed);
    }

    @QueryMapping
    public List<PetNameTypeBreedDTO> getNameAndBreedOnly() {
        return petService.getNameTypeAndBreedOnly();
    }

   // @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @QueryMapping
    public Object getPetStatistics() {
        return petService.getPetStatistics();
    }

   // @Secured("ROLE_ADMIN")
    @MutationMapping
    public Pet createPet(@Argument @Valid PetDTO input) {
        Household household = householdService.getHouseholdByEircode(input.eircode());
        Pet pet = new Pet(null, input.name(), input.animalType(), input.breed(), input.age(), household);
        return petService.createPet(pet);
    }

  //  @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @MutationMapping
    public Pet updatePet(@Argument @Valid PetDTO input) {
        if (input.id() == null) {
            throw new IllegalArgumentException("ID is required for updating a pet");
        }
        Pet updatedPet = new Pet(input.id(), input.name(), input.animalType(), input.breed(), input.age(), null);
        return petService.updatePet(input.id(), updatedPet);
    }

   // @Secured("ROLE_ADMIN")
    @MutationMapping
    public boolean deletePetById(@Argument Long id) {
        petService.deletePetById(id);
        return true;
    }

 //   @Secured("ROLE_ADMIN")
    @MutationMapping
    public boolean deletePetsByName(@Argument String name) {
        petService.deletePetsByName(name);
        return true;
    }
}
