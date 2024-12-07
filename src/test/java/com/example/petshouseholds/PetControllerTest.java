package com.example.petshouseholds;

import com.example.petshouseholds.controller.PetController;
import com.example.petshouseholds.dto.PetDTO;
import com.example.petshouseholds.dto.PetNameTypeBreedDTO;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all pets")
    void testGetAllPets() throws Exception {
        Pet pet = new Pet(1L, "Buddy", "Dog", "Labrador", 3, null);
        when(petService.getAllPets()).thenReturn(List.of(pet));

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Buddy")));

        verify(petService, times(1)).getAllPets();
    }

    @Test
    @DisplayName("Get pet by ID")
    void testGetPetById() throws Exception {
        Pet pet = new Pet(1L, "Buddy", "Dog", "Labrador", 3, null);
        when(petService.getPetById(1L)).thenReturn(pet);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Buddy")));

        verify(petService, times(1)).getPetById(1L);
    }

    @Test
    @DisplayName("Create a pet")
    void testCreatePet() throws Exception {
        PetDTO petDTO = new PetDTO(null, "Buddy", "Dog", "Labrador", 3, "EIR123");
        Pet createdPet = new Pet(1L, "Buddy", "Dog", "Labrador", 3, null);

        when(petService.createPet(any(Pet.class))).thenReturn(createdPet);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Buddy")));

        verify(petService, times(1)).createPet(any(Pet.class));
    }

    @Test
    @DisplayName("Delete pets by name")
    void testDeletePetsByName() throws Exception {
        doNothing().when(petService).deletePetsByName("Buddy");

        mockMvc.perform(delete("/api/pets/name/Buddy"))
                .andExpect(status().isNoContent());

        verify(petService, times(1)).deletePetsByName("Buddy");
    }
}
