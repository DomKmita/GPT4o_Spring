package com.example.petshouseholds;

import com.example.petshouseholds.entity.Household;
import com.example.petshouseholds.entity.Pet;
import com.example.petshouseholds.exception.NotFoundException;
import com.example.petshouseholds.repository.HouseholdRepository;
import com.example.petshouseholds.repository.PetRepository;
import com.example.petshouseholds.service.impl.PetServiceImpl;
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

class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Pet - Valid Household")
    void testCreatePetWithValidHousehold() {
        Household household = new Household("EIR123", 3, 5, true, null);
        Pet pet = new Pet(null, "Buddy", "Dog", "Labrador", 3, household);

        when(householdRepository.findById("EIR123")).thenReturn(Optional.of(household));
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet createdPet = petService.createPet(pet);

        assertThat(createdPet.getName()).isEqualTo("Buddy");
        verify(householdRepository, times(1)).findById("EIR123");
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    @DisplayName("Create Pet - Invalid Household")
    void testCreatePetWithInvalidHousehold() {
        Pet pet = new Pet(null, "Buddy", "Dog", "Labrador", 3, new Household("EIR123", 3, 5, true, null));

        when(householdRepository.findById("EIR123")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> petService.createPet(pet))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Household not found");

        verify(householdRepository, times(1)).findById("EIR123");
        verifyNoInteractions(petRepository);
    }
}
