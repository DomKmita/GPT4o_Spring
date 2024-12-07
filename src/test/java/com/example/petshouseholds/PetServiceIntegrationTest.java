package com.example.petshouseholds;

import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.service.PetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"/test-data.sql"})
class PetServiceIntegrationTest {

    @Autowired
    private PetService petService;

    @Test
    @DisplayName("Get All Pets - Integration Test")
    void testGetAllPets() {
        List<Pet> pets = petService.getAllPets();
        assertThat(pets).hasSize(4); // Matches test-data.sql
    }

    @Test
    @DisplayName("Get Pet By ID - Integration Test")
    void testGetPetById() {
        Pet pet = petService.getPetById(1L);
        assertThat(pet.getName()).isEqualTo("Buddy");
    }
}
