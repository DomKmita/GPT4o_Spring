package com.example.petshouseholds;

import com.example.petshouseholds.dto.PetDTO;
import com.example.petshouseholds.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNameIgnoreCase(String name);
    List<Pet> findByAnimalType(String animalType);
    List<Pet> findByBreedOrderByAge(String breed);
    @Query("SELECT new com.example.dto.PetDTO(p.name, p.animalType, p.breed) FROM Pet p")
    List<PetDTO> findNameAndBreedOnly();
}
