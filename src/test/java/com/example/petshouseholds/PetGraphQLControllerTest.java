package com.example.petshouseholds;
import com.example.petshouseholds.controller.PetGraphQLController;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.service.PetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@GraphQlTest(PetGraphQLController.class)
class PetGraphQLControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private PetService petService;

    @Test
    @DisplayName("Query: Get all pets")
    void testGetAllPets() {
        Pet pet = new Pet(1L, "Buddy", "Dog", "Labrador", 3, null);
        when(petService.getAllPets()).thenReturn(List.of(pet));

        graphQlTester.document("""
                query {
                    getAllPets {
                        id
                        name
                        animalType
                        breed
                        age
                    }
                }
                """)
                .execute()
                .path("getAllPets")
                .entityList(Pet.class)
                .hasSize(1)
                .contains(pet);

        verify(petService, times(1)).getAllPets();
    }

    @Test
    @DisplayName("Mutation: Create pet")
    void testCreatePet() {
        Pet pet = new Pet(1L, "Buddy", "Dog", "Labrador", 3, null);
        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        graphQlTester.document("""
            mutation {
                createPet(input: {
                    name: "Buddy",
                    animalType: "Dog",
                    breed: "Labrador",
                    age: 3,
                    eircode: "EIR123"
                }) {
                    id
                    name
                    animalType
                    breed
                    age
                }
            }
            """)
                .execute()
                .path("createPet")
                .entity(Pet.class)
                .isEqualTo(pet);

        verify(petService, times(1)).createPet(any(Pet.class));
    }

    @Test
    @DisplayName("Query: Get pet statistics")
    void testGetPetStatistics() {
        Map<String, Object> stats = Map.of("averageAge", 3.5, "oldestAge", 5, "totalCount", 2);
        when(petService.getPetStatistics()).thenReturn(stats);

        graphQlTester.document("""
                query {
                    getPetStatistics {
                        averageAge
                        oldestAge
                        totalCount
                    }
                }
                """)
                .execute()
                .path("getPetStatistics")
                .entity(Map.class)
                .isEqualTo(stats);

        verify(petService, times(1)).getPetStatistics();
    }
}
