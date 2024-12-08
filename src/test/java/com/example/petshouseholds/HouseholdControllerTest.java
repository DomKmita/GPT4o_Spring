package com.example.petshouseholds;

import com.example.petshouseholds.controller.HouseholdController;
import com.example.petshouseholds.dto.HouseholdDTO;
import com.example.petshouseholds.dto.HouseholdWithoutPetsDTO;
import com.example.petshouseholds.entity.Household;
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

@WebMvcTest(HouseholdController.class)
class HouseholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseholdService householdService;

    @Test
    void testGetHouseholdWithoutPets_Anyone() throws Exception {
        HouseholdWithoutPetsDTO household = new HouseholdWithoutPetsDTO("EIR123", 3, 5, true);
        when(householdService.getHouseholdByEircodeNoPets("EIR123")).thenReturn(household);

        mockMvc.perform(get("/api/households/EIR123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode").value("EIR123"))
                .andExpect(jsonPath("$.numberOfOccupants").value(3))
                .andExpect(jsonPath("$.ownerOccupied").value(true));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateHousehold_Admin() throws Exception {
        HouseholdDTO householdDTO = new HouseholdDTO("EIR123", 3, 5, true);
        Household household = new Household("EIR123", 3, 5, true, null);

        when(householdService.createHousehold(any(Household.class))).thenReturn(household);

        mockMvc.perform(post("/api/households")
                        .contentType(APPLICATION_JSON)
                        .content("""
                        {
                            "eircode": "EIR123",
                            "numberOfOccupants": 3,
                            "maxNumberOfOccupants": 5,
                            "ownerOccupied": true
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode").value("EIR123"))
                .andExpect(jsonPath("$.numberOfOccupants").value(3));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateHousehold_UserForbidden() throws Exception {
        mockMvc.perform(post("/api/households")
                        .contentType(APPLICATION_JSON)
                        .content("""
                        {
                            "eircode": "EIR123",
                            "numberOfOccupants": 3,
                            "maxNumberOfOccupants": 5,
                            "ownerOccupied": true
                        }
                        """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteHousehold_Admin() throws Exception {
        mockMvc.perform(delete("/api/households/EIR123"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDeleteHousehold_UserForbidden() throws Exception {
        mockMvc.perform(delete("/api/households/EIR123"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testUpdateHousehold_User() throws Exception {
        HouseholdDTO householdDTO = new HouseholdDTO("EIR123", 4, 5, false);
        Household updatedHousehold = new Household("EIR123", 4, 5, false, null);

        when(householdService.updateHousehold("EIR123", any(Household.class))).thenReturn(updatedHousehold);

        mockMvc.perform(put("/api/households/EIR123")
                        .contentType(APPLICATION_JSON)
                        .content("""
                        {
                            "eircode": "EIR123",
                            "numberOfOccupants": 4,
                            "maxNumberOfOccupants": 5,
                            "ownerOccupied": false
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfOccupants").value(4))
                .andExpect(jsonPath("$.ownerOccupied").value(false));
    }
}
