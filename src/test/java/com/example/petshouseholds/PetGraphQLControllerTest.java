package com.example.petshouseholds;

import com.example.petshouseholds.controller.PetGraphQLController;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.service.PetService;
import com.example.petshouseholds.service.HouseholdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest(PetGraphQLController.class)
class PetGraphQLControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private PetService petService;

    @MockBean
    private HouseholdService householdService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreatePet_Admin() {
        when(householdService.getHouseholdByEircode("EIR123")).thenReturn(new Household());
        when(petService.createPet(any())).thenReturn(new Pet());

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
                .matches(pet -> pet.getName().equals("Buddy"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreatePet_UserForbidden() {
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
                .errors()
                .satisfy(errors -> errors.stream()
                        .anyMatch(error -> error.getMessage().contains("Access is denied")));
    }
}
