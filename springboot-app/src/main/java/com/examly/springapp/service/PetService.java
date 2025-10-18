package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.exception.PetNotFoundException;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.Pet;

/**
 * Service interface for handling pet-related operations.
 */
public interface PetService {

    /**
     * Adds a new pet.
     * 
     * @param pet The pet to be added
     * @return The added pet
     */
    Pet addPet(Pet pet);

    /**
     * Retrieves a pet by its ID.
     * 
     * @param petId The ID of the pet to be retrieved
     * @return The retrieved pet, or null if not found
     */
    Pet getPetById(int petId) throws PetNotFoundException;

    /**
     * Updates a pet by its ID.
     * 
     * @param petId The ID of the pet to be updated
     * @param pet   The updated pet details
     * @return The updated pet, or null if not found
     */
    Pet updatePetById(int petId, Pet pet) throws PetNotFoundException;

    /**
     * Deletes a pet by its ID.
     * 
     * @param petId The ID of the pet to be deleted
     * @return true if the pet was successfully deleted, false otherwise
     * @throws PetNotFoundException
     */
    boolean deletePetById(int petId) throws PetNotFoundException;

    /**
     * Retrieves all pets for a specific user by the user's ID.
     * 
     * @param userId The ID of the user whose pets are to be retrieved
     * @return A list of pets belonging to the specified user
     */
    List<Pet> getAllPetsByUserId(int userId) throws UserNotFoundException;

    String getPetNameById(Integer petId);

}