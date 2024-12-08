package com.example.petshouseholds.service.impl;

import com.example.petshouseholds.dto.PetNameTypeBreedDTO;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.exception.NotFoundException;
import com.example.petshouseholds.repository.PetRepository;
import com.example.petshouseholds.repository.HouseholdRepository;
import com.example.petshouseholds.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final HouseholdRepository householdRepository;

    @Override
    public Pet createPet(Pet pet) {
        Household household = householdRepository.findById(pet.getHousehold().getEircode())
                .orElseThrow(() -> new NotFoundException("Household not found"));
        pet.setHousehold(household);
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found"));
    }

    @Override
    public Pet updatePet(Long id, Pet updatedPet) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found"));
        pet.setName(updatedPet.getName());
        pet.setAnimalType(updatedPet.getAnimalType());
        pet.setBreed(updatedPet.getBreed());
        pet.setAge(updatedPet.getAge());
        return petRepository.save(pet);
    }

    @Override
    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet not found");
        }
        petRepository.deleteById(id);
    }

    @Override
    public void deletePetsByName(String name) {
        List<Pet> pets = petRepository.findByNameIgnoreCase(name);
        petRepository.deleteAll(pets);
    }

    @Override
    public List<Pet> findPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalType(animalType);
    }

    @Override
    public List<Pet> findPetsByBreed(String breed) {
        return petRepository.findByBreedOrderByAge(breed);
    }

    @Override
    public List<PetNameTypeBreedDTO> getNameTypeAndBreedOnly() {
        return petRepository.findNameTypeAndBreedOnly();
    }

    @Override
    public Object getPetStatistics() {
        List<Pet> pets = petRepository.findAll();
        double averageAge = pets.stream().mapToInt(Pet::getAge).average().orElse(0.0);
        int oldestAge = pets.stream().mapToInt(Pet::getAge).max().orElse(0);
        return Map.of("averageAge", averageAge, "oldestAge", oldestAge, "totalCount", pets.size());
    }
}
