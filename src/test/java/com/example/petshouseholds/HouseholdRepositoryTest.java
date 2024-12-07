//package com.example.petshouseholds;
//
//import com.example.petshouseholds.repository.HouseholdRepository;
//import com.example.petshouseholds.entity.Household;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.jdbc.Sql;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@Sql(scripts = {"/test-data.sql"})
//public class HouseholdRepositoryTest {
//
//    @Autowired
//    private HouseholdRepository householdRepository;
//
//    @Test
//    @DisplayName("Find households with no pets")
//    void testFindHouseholdsWithNoPets() {
//        List<Household> households = householdRepository.findHouseholdsWithNoPets();
//        assertThat(households).hasSize(3); // Adjust to match the test data
//        assertThat(households.stream().map(Household::getEircode))
//                .containsExactlyInAnyOrder("EIR789", "EIR202", "EIR101");
//    }
//
//    @Test
//    @DisplayName("Find owner-occupied households")
//    void testFindOwnerOccupiedHouseholds() {
//        List<Household> households = householdRepository.findByOwnerOccupied(true);
//        assertThat(households).hasSize(3);
//    }
//
//    @Test
//    @DisplayName("Count empty houses")
//    void testCountEmptyHouses() {
//        Long emptyHouses = householdRepository.countEmptyHouses();
//        assertThat(emptyHouses).isEqualTo(1);
//    }
//
//    @Test
//    @DisplayName("Count full houses")
//    void testCountFullHouses() {
//        Long fullHouses = householdRepository.countFullHouses();
//        assertThat(fullHouses).isEqualTo(1);
//    }
//
//    @Test
//    @DisplayName("Find household by eircode with pets eagerly")
//    void testFindByIdWithPets() {
//        Household household = householdRepository.findByIdWithPets("EIR123")
//                .orElseThrow(() -> new RuntimeException("Household not found"));
//        assertThat(household.getPets()).hasSize(2);
//    }
//}
