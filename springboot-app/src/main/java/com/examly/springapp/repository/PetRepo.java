package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Pet;

/**
 * Repository interface for Pet entity.
 * Extends JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface PetRepo extends JpaRepository<Pet, Integer> {

    /**
     * Custom query to retrieve all pets associated with a particular user by user
     * ID.
     *
     * @param userId the ID of the user whose pets are to be retrieved.
     * @return a list of pets associated with the specified user ID.
     */
    @Query(value = "select * from pet where user_id = ?1", nativeQuery = true)
    List<Pet> findAllPetsByUserId(int userId);

    @Query("SELECT p FROM Pet p WHERE p.petId IN :petIds")
    List<Pet> findPetsByIds(@Param("petIds") List<Integer> petIds);

    void deleteByPetId(int petId);
}