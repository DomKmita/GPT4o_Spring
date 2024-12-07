package com.example.petshouseholds;

import com.example.petshouseholds.dto.PetNameTypeBreedDTO;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = {"/test-data.sql"})
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Test
    @DisplayName("Find pets by name ignoring case")
    void testFindPetsByNameIgnoreCase() {
        List<Pet> pets = petRepository.findByNameIgnoreCase("buddy");
        assertThat(pets).hasSize(1);
        assertThat(pets.get(0).getAnimalType()).isEqualTo("Dog");
    }

    @Test
    @DisplayName("Find pets by animal type")
    void testFindPetsByAnimalType() {
        List<Pet> pets = petRepository.findByAnimalType("Cat");
        assertThat(pets).hasSize(1);
        assertThat(pets.get(0).getName()).isEqualTo("Mittens");
    }

    @Test
    @DisplayName("Find pets by breed ordered by age")
    void testFindPetsByBreedOrderByAge() {
        List<Pet> pets = petRepository.findByBreedOrderByAge("Labrador");
        assertThat(pets).hasSize(1);
        assertThat(pets.get(0).getName()).isEqualTo("Buddy");
    }

    @Test
    @DisplayName("Find name, animal type, and breed only")
    void testFindNameTypeAndBreedOnly() {
        List<PetNameTypeBreedDTO> pets = petRepository.findNameTypeAndBreedOnly();
        assertThat(pets).hasSizeGreaterThanOrEqualTo(1);
        PetNameTypeBreedDTO pet = pets.get(0);
        assertThat(pet.name()).isEqualTo("Buddy");
        assertThat(pet.animalType()).isEqualTo("Dog");
        assertThat(pet.breed()).isEqualTo("Labrador");
    }
}
