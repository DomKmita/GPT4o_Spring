package com.example.petshouseholds;

import com.example.petshouseholds.controller.HouseholdController;
import com.example.petshouseholds.dto.HouseholdDTO;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.service.HouseholdService;
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

@WebMvcTest(HouseholdController.class)
class HouseholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseholdService householdService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all households")
    void testGetAllHouseholds() throws Exception {
        Household household = new Household("EIR123", 3, 5, true, null);
        when(householdService.getAllHouseholds()).thenReturn(List.of(household));

        mockMvc.perform(get("/api/households"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].eircode", is("EIR123")));

        verify(householdService, times(1)).getAllHouseholds();
    }

    @Test
    @DisplayName("Create a household")
    void testCreateHousehold() throws Exception {
        HouseholdDTO householdDTO = new HouseholdDTO("EIR123", 3, 5, true);
        Household createdHousehold = new Household("EIR123", 3, 5, true, null);

        when(householdService.createHousehold(any(Household.class))).thenReturn(createdHousehold);

        mockMvc.perform(post("/api/households")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(householdDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode", is("EIR123")));

        verify(householdService, times(1)).createHousehold(any(Household.class));
    }

    @Test
    @DisplayName("Get household by eircode without pets")
    void testGetHouseholdByEircodeNoPets() throws Exception {
        Household household = new Household("EIR123", 3, 5, true, null);
        when(householdService.getHouseholdByEircodeNoPets("EIR123")).thenReturn(household);

        mockMvc.perform(get("/api/households/EIR123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode", is("EIR123")));

        verify(householdService, times(1)).getHouseholdByEircodeNoPets("EIR123");
    }
}
