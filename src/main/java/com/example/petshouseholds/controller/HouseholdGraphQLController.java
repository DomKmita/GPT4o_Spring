package com.example.petshouseholds.controller;

import com.example.petshouseholds.dto.HouseholdDTO;
import com.example.petshouseholds.dto.HouseholdStatisticsDTO;
import com.example.petshouseholds.dto.HouseholdWithoutPetsDTO;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.service.HouseholdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HouseholdGraphQLController {

    private final HouseholdService householdService;

    @QueryMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public HouseholdWithoutPetsDTO getHouseholdByEircodeNoPets(@Argument String eircode) {
        return householdService.getHouseholdByEircodeNoPets(eircode);
    }

    @QueryMapping
    public Household getHouseholdByEircodeWithPets(@Argument String eircode) {
        return householdService.getHouseholdByEircodeWithPets(eircode);
    }

    @QueryMapping
    public List<Household> findHouseholdsWithNoPets() {
        return householdService.findHouseholdsWithNoPets();
    }

    @QueryMapping
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdService.findOwnerOccupiedHouseholds();
    }

    @QueryMapping
    public HouseholdStatisticsDTO getHouseholdStatistics() {
        return householdService.getHouseholdStatistics();
    }

    //@Secured("ROLE_ADMIN")
    @MutationMapping
    public Household createHousehold(@Argument @Valid HouseholdDTO input) {
        Household household = new Household(input.eircode(), input.numberOfOccupants(),
                input.maxNumberOfOccupants(), input.ownerOccupied(), null);
        return householdService.createHousehold(household);
    }

    //@Secured({"ROLE_USER", "ROLE_ADMIN"})
    @MutationMapping
    public Household updateHousehold(@Argument @Valid HouseholdDTO input) {
        Household household = new Household(input.eircode(), input.numberOfOccupants(),
                input.maxNumberOfOccupants(), input.ownerOccupied(), null);
        return householdService.updateHousehold(input.eircode(), household);
    }

   // @Secured("ROLE_ADMIN")
    @MutationMapping
    public boolean deleteHouseholdByEircode(@Argument String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return true;
    }
}
