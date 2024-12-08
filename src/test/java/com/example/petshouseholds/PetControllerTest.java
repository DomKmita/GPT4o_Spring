package com.example.petshouseholds;

import com.example.petshouseholds.controller.PetController;
import com.example.petshouseholds.dto.PetDTO;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.service.PetService;
import com.example.petshouseholds.service.HouseholdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @MockBean
    private HouseholdService householdService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreatePet_Admin() throws Exception {
        PetDTO petDTO = new PetDTO(null, "Buddy", "Dog", "Labrador", 3, "EIR123");
        Household household = new Household("EIR123", 3, 5, true, null);
        Pet pet = new Pet(1L, "Buddy", "Dog", "Labrador", 3, household);

        when(householdService.getHouseholdByEircode("EIR123")).thenReturn(household);
        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/api/pets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Buddy",
                            "animalType": "Dog",
                            "breed": "Labrador",
                            "age": 3,
                            "eircode": "EIR123"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreatePet_UserForbidden() throws Exception {
        mockMvc.perform(post("/api/pets")
                        .contentType(APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Buddy",
                            "animalType": "Dog",
                            "breed": "Labrador",
                            "age": 3,
                            "eircode": "EIR123"
                        }
                        """))
                .andExpect(status().isForbidden());
    }
}
