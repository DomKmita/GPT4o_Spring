package com.example.petshouseholds;

import com.example.petshouseholds.controller.HouseholdGraphQLController;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.service.HouseholdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest(HouseholdGraphQLController.class)
class HouseholdGraphQLControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private HouseholdService householdService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateHousehold_Admin() {
        Household household = new Household("EIR123", 3, 5, true, null);

        when(householdService.createHousehold(any(Household.class))).thenReturn(household);

        graphQlTester.document("""
                mutation {
                    createHousehold(input: {
                        eircode: "EIR123",
                        numberOfOccupants: 3,
                        maxNumberOfOccupants: 5,
                        ownerOccupied: true
                    }) {
                        eircode
                        numberOfOccupants
                        maxNumberOfOccupants
                        ownerOccupied
                    }
                }
                """)
                .execute()
                .path("createHousehold.eircode").entity(String.class).isEqualTo("EIR123");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateHousehold_UserForbidden() {
        graphQlTester.document("""
                mutation {
                    createHousehold(input: {
                        eircode: "EIR123",
                        numberOfOccupants: 3,
                        maxNumberOfOccupants: 5,
                        ownerOccupied: true
                    }) {
                        eircode
                        numberOfOccupants
                        maxNumberOfOccupants
                        ownerOccupied
                    }
                }
                """)
                .execute()
                .errors()
                .satisfy(errors -> errors.stream()
                        .anyMatch(error -> error.getMessage().contains("Access is denied")));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testUpdateHousehold_User() {
        Household updatedHousehold = new Household("EIR123", 4, 5, false, null);

        when(householdService.updateHousehold("EIR123", any(Household.class))).thenReturn(updatedHousehold);

        graphQlTester.document("""
                mutation {
                    updateHousehold(input: {
                        eircode: "EIR123",
                        numberOfOccupants: 4,
                        maxNumberOfOccupants: 5,
                        ownerOccupied: false
                    }) {
                        eircode
                        numberOfOccupants
                        ownerOccupied
                    }
                }
                """)
                .execute()
                .path("updateHousehold.numberOfOccupants").entity(Integer.class).isEqualTo(4)
                .path("updateHousehold.ownerOccupied").entity(Boolean.class).isEqualTo(false);
    }
}
