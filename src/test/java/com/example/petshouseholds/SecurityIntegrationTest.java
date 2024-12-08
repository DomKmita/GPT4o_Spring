package com.example.petshouseholds;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPublicEndpointAccess() throws Exception {
        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAdminAccess() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("""
                        {
                            "username": "testuser",
                            "password": "password123",
                            "role": "ROLE_USER",
                            "unlocked": true
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testUserForbiddenAccessToAdminEndpoints() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("""
                        {
                            "username": "testuser",
                            "password": "password123",
                            "role": "ROLE_USER",
                            "unlocked": true
                        }
                        """))
                .andExpect(status().isForbidden());
    }
}
