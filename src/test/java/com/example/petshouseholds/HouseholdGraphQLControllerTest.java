package com.example.petshouseholds;

import com.example.petshouseholds.controller.HouseholdGraphQLController;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.service.HouseholdService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import java.util.List;
import static org.mockito.Mockito.*;

@GraphQlTest(HouseholdGraphQLController.class)
class HouseholdGraphQLControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private HouseholdService householdService;

    @Test
    @DisplayName("Query: Get all households")
    void testGetAllHouseholds() {
        Household household = new Household("EIR123", 3, 5, true, null);
        when(householdService.getAllHouseholds()).thenReturn(List.of(household));

        graphQlTester.document("""
                query {
                    getAllHouseholds {
                        eircode
                        numberOfOccupants
                        maxNumberOfOccupants
                        ownerOccupied
                    }
                }
                """)
                .execute()
                .path("getAllHouseholds")
                .entityList(Household.class)
                .hasSize(1)
                .contains(household);

        verify(householdService, times(1)).getAllHouseholds();
    }

    @Test
    @DisplayName("Query: Get household by eircode (no pets)")
    void testGetHouseholdByEircodeNoPets() {
        Household household = new Household("EIR123", 3, 5, true, null);
        when(householdService.getHouseholdByEircodeNoPets("EIR123")).thenReturn(household);

        graphQlTester.document("""
            query {
                getHouseholdByEircodeNoPets(eircode: "EIR123") {
                    eircode
                    numberOfOccupants
                    maxNumberOfOccupants
                    ownerOccupied
                }
            }
            """)
                .execute()
                .path("getHouseholdByEircodeNoPets")
                .entity(Household.class)
                .isEqualTo(household);

        verify(householdService, times(1)).getHouseholdByEircodeNoPets("EIR123");
    }

    @Test
    @DisplayName("Mutation: Delete household by eircode")
    void testDeleteHouseholdByEircode() {
        doNothing().when(householdService).deleteHouseholdByEircode("EIR123");

        graphQlTester.document("""
                mutation {
                    deleteHouseholdByEircode(eircode: "EIR123")
                }
                """)
                .execute()
                .path("deleteHouseholdByEircode")
                .entity(Boolean.class)
                .isEqualTo(true);

        verify(householdService, times(1)).deleteHouseholdByEircode("EIR123");
    }
}
