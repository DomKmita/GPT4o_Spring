package com.example.petshouseholds.repository;

import com.example.petshouseholds.entity.Household;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, String> {
    @Query("SELECT h FROM Household h WHERE h.pets IS EMPTY")
    List<Household> findHouseholdsWithNoPets();

    // Default method to load household without pets (lazy behavior)
    Optional<Household> findById(String eircode);

    // Custom method to eagerly load pets using @EntityGraph
    @EntityGraph(attributePaths = {"pets"})
    @Query("SELECT h FROM Household h WHERE h.eircode = :eircode")
    Optional<Household> findByIdWithPets(@Param("eircode") String eircode);

    List<Household> findByOwnerOccupied(boolean ownerOccupied);

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = 0")
    Long countEmptyHouses();

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = h.maxNumberOfOccupants")
    Long countFullHouses();
}
