package com.example.petshouseholds;

import com.example.petshouseholds.entities.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, String> {
    @Query("SELECT h FROM Household h WHERE h.pets IS EMPTY")
    List<Household> findHouseholdsWithNoPets();

    List<Household> findByOwnerOccupied(boolean ownerOccupied);

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = 0")
    Long countEmptyHouses();

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = h.maxNumberOfOccupants")
    Long countFullHouses();
}
