package com.example.petshouseholds;

import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.exception.NotFoundException;
import com.example.petshouseholds.repository.HouseholdRepository;
import com.example.petshouseholds.service.impl.HouseholdServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class HouseholdServiceTest {

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private HouseholdServiceImpl householdService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get Household By Eircode - No Pets")
    void testGetHouseholdByEircodeNoPets() {
        Household household = new Household("EIR123", 3, 5, true, null);

        when(householdRepository.findById("EIR123")).thenReturn(Optional.of(household));

        Household retrievedHousehold = householdService.getHouseholdByEircodeNoPets("EIR123");

        assertThat(retrievedHousehold.getEircode()).isEqualTo("EIR123");
        verify(householdRepository, times(1)).findById("EIR123");
    }

    @Test
    @DisplayName("Get Household By Eircode - Not Found")
    void testGetHouseholdByEircodeNotFound() {
        when(householdRepository.findById("EIR123")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> householdService.getHouseholdByEircodeNoPets("EIR123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Household not found");

        verify(householdRepository, times(1)).findById("EIR123");
    }
}
