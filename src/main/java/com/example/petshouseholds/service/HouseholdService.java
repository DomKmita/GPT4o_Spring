package com.example.petshouseholds.service;

import com.example.petshouseholds.dto.HouseholdWithoutPetsDTO;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.dto.HouseholdStatisticsDTO;

import java.util.List;

public interface HouseholdService {

    Household createHousehold(Household household);

    List<Household> getAllHouseholds();

    HouseholdWithoutPetsDTO getHouseholdByEircodeNoPets(String eircode);

    Household getHouseholdByEircodeWithPets(String eircode);

    Household updateHousehold(String eircode, Household updatedHousehold);

    void deleteHouseholdByEircode(String eircode);

    List<Household> findHouseholdsWithNoPets();

    List<Household> findOwnerOccupiedHouseholds();

    HouseholdStatisticsDTO getHouseholdStatistics();
    Household getHouseholdByEircode(String eircode);


}
