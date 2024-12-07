package com.example.petshouseholds;

import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.service.HouseholdService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"/test-data.sql"})
class HouseholdServiceIntegrationTest {

    @Autowired
    private HouseholdService householdService;

    @Test
    @DisplayName("Get All Households - Integration Test")
    void testGetAllHouseholds() {
        List<Household> households = householdService.getAllHouseholds();
        assertThat(households).hasSize(5); // Matches test-data.sql
    }

    @Test
    @DisplayName("Get Household By Eircode With Pets - Integration Test")
    void testGetHouseholdByEircodeWithPets() {
        Household household = householdService.getHouseholdByEircodeWithPets("EIR123");
        assertThat(household.getPets()).hasSize(2); // Matches test-data.sql
    }
}
