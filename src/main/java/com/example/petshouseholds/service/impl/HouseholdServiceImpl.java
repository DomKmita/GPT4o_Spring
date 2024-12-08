package com.example.petshouseholds.service.impl;

import com.example.petshouseholds.dto.HouseholdStatisticsDTO;
import com.example.petshouseholds.dto.HouseholdWithoutPetsDTO;
import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.exception.NotFoundException;
import com.example.petshouseholds.repository.HouseholdRepository;
import com.example.petshouseholds.service.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    @Override
    public Household createHousehold(Household household) {
        return householdRepository.save(household);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public HouseholdWithoutPetsDTO getHouseholdByEircodeNoPets(String eircode) {
        Household household = householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found"));
        return new HouseholdWithoutPetsDTO(
                household.getEircode(),
                household.getNumberOfOccupants(),
                household.getMaxNumberOfOccupants(),
                household.isOwnerOccupied()
        );
    }

    // Custom method: Pets loaded eagerly using @EntityGraph
    @Override
    public Household getHouseholdByEircodeWithPets(String eircode) {
        return householdRepository.findByIdWithPets(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found"));
    }

    @Override
    public Household getHouseholdByEircode(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found"));
    }

    @Override
    public Household updateHousehold(String eircode, Household updatedHousehold) {
        Household household = householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found"));
        household.setNumberOfOccupants(updatedHousehold.getNumberOfOccupants());
        household.setMaxNumberOfOccupants(updatedHousehold.getMaxNumberOfOccupants());
        household.setOwnerOccupied(updatedHousehold.isOwnerOccupied());
        return householdRepository.save(household);
    }

    @Override
    public void deleteHouseholdByEircode(String eircode) {
        if (!householdRepository.existsById(eircode)) {
            throw new NotFoundException("Household not found");
        }
        householdRepository.deleteById(eircode);
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdRepository.findByOwnerOccupied(true);
    }

    @Override
    public HouseholdStatisticsDTO getHouseholdStatistics() {
        int emptyHouses = householdRepository.countEmptyHouses().intValue();
        int fullHouses = householdRepository.countFullHouses().intValue();
        return new HouseholdStatisticsDTO(emptyHouses, fullHouses);
    }
}
