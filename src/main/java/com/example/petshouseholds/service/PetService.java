package com.example.petshouseholds.service;

import com.example.petshouseholds.dto.PetNameTypeBreedDTO;
import com.example.petshouseholds.entity.Pet;

import java.util.List;

public interface PetService {

    Pet createPet(Pet pet);

    List<Pet> getAllPets();

    Pet getPetById(Long id);

    Pet updatePet(Long id, Pet updatedPet);

    void deletePetById(Long id);

    void deletePetsByName(String name);

    List<Pet> findPetsByAnimalType(String animalType);

    List<Pet> findPetsByBreed(String breed);

    List<PetNameTypeBreedDTO> getNameTypeAndBreedOnly();

    Object getPetStatistics();
}
