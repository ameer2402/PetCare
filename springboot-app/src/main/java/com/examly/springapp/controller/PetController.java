package com.examly.springapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exception.PetNotFoundException;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.Pet;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.service.PetServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller class to handle pet-related operations.
 */
@RestController
@Tag(name = "Pet", description = "Operations pertaining to pets in Pet Care Application")
public class PetController {

    private final PetServiceImpl petServiceImpl;
    private final UserRepo userRepo;

    /**
     * Constructor injection for PetServiceImpl.
     *
     * @param petServiceImpl The service to handle pet operations.
     */
    public PetController(PetServiceImpl petServiceImpl, UserRepo userRepo) {
        this.petServiceImpl = petServiceImpl;
        this.userRepo = userRepo;
    }

    /**
     * HTTP POST endpoint to add a new pet.
     *
     * @param pet The pet to be added.
     * @return The created pet.
     */
    @Operation(summary = "Add a new pet", description = "Save a new pet for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet successfully added"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    // @PreAuthorize("hasRole('PETOWNER')")
    @PostMapping("api/pet")
    public ResponseEntity<Pet> addPet(@Valid @RequestBody Pet pet) {
        Pet pets = petServiceImpl.addPet(pet);
        if (pets == null) {
            // Return HTTP 400 if pet addition fails
            return ResponseEntity.status(400).body(null);
        }
        // Return HTTP 201 if pet is added successfully
        return ResponseEntity.status(201).body(pets);
    }

    /**
     * HTTP GET endpoint to get a pet by its ID.
     *
     * @param petId The ID of the pet to be retrieved.
     * @return The pet with the specified ID.
     */
    @Operation(summary = "Get pet by ID", description = "Retrieve details of a pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Pet not found")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    // @PostAuthorize("returnObject.body.user.email == authentication.name")
    @GetMapping("/api/pet/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable int petId) throws PetNotFoundException {
        Pet getPet = petServiceImpl.getPetById(petId);
        if (getPet != null) {
            // Return HTTP 200 if pet is found
            return ResponseEntity.status(200).body(getPet);
        }
        throw new PetNotFoundException("Pet with petId " + petId + " not found");
    }

    /**
     * HTTP PUT endpoint to update a pet by its ID.
     *
     * @param petId The ID of the pet to be updated.
     * @param pet   The updated pet details.
     * @return The updated pet.
     */
    @Operation(summary = "Update pet by ID", description = "Update the details of an existing pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet successfully updated"),
            @ApiResponse(responseCode = "400", description = "Pet not found")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    // @PostAuthorize("returnObject.body.user.email == authentication.name ")
    @PutMapping("/api/pet/{petId}")
    public ResponseEntity<Pet> updatePetById(@PathVariable int petId, @Valid @RequestBody Pet pet)
            throws PetNotFoundException {
        Pet updatePets = petServiceImpl.updatePetById(petId, pet);
        if (updatePets != null) {
            // Return HTTP 200 if pet is updated successfully
            return ResponseEntity.status(200).body(updatePets);
        }
        throw new PetNotFoundException("Pet with petId " + petId + " not found");
    }

    /**
     * HTTP DELETE endpoint to delete a pet by its ID.
     *
     * @param petId The ID of the pet to be deleted.
     * @return A message indicating the deletion status.
     * @throws PetNotFoundException If the pet is not found.
     */
    @Operation(summary = "Delete pet by ID", description = "Delete an existing pet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    // @DeleteMapping("/api/pet/{petId}")
    // public ResponseEntity<String> deletePetById(@PathVariable int petId) throws
    // PetNotFoundException {
    // boolean flag = petServiceImpl.deletePetById(petId);
    // if (flag) {
    // // Return HTTP 200 if pet is deleted successfully
    // return ResponseEntity.status(200).body("Pet with id "+ petId +" deleted
    // successfully!");
    // }
    // throw new PetNotFoundException("Pet with petId "+petId+" not found");
    // }
    @DeleteMapping("/api/pet/{petId}")
    public ResponseEntity<Map<String, String>> deletePetById(@PathVariable int petId) throws PetNotFoundException {
        boolean flag = petServiceImpl.deletePetById(petId);
        if (flag) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Pet with id " + petId + " deleted successfully!");
            return ResponseEntity.status(200).body(response);
        }
        throw new PetNotFoundException("Pet with petId " + petId + " not found");
    }

    /**
     * HTTP GET endpoint to get all pets by a user's ID.
     *
     * @param userId The ID of the user whose pets are to be retrieved.
     * @return A list of pets for the specified user.
     */
    @Operation(summary = "Get all pets by user ID", description = "Retrieve a list of all pets for a specific user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pets successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    // @PostAuthorize("returnObject.body.stream().allMatch(pet ->
    // pet.getUser().getEmail().equals(authentication.name))")
    @GetMapping("/api/pet/user/{userId}")
    public ResponseEntity<List<Pet>> getAllPetsByUserId(@PathVariable int userId) throws UserNotFoundException {
        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException("User with UserId " + userId + " not found.");
        }
        List<Pet> getAllPet = petServiceImpl.getAllPetsByUserId(userId);
        if (getAllPet != null) {
            // Return HTTP 200 if pets are found
            return ResponseEntity.status(200).body(getAllPet);
        }
        // Return HTTP 500 if fetching pets fails
        return ResponseEntity.status(500).body(null);
    }

    @PostMapping("/pets/by-ids")
    public List<Pet> getPetsByIds(@RequestBody List<Integer> petIds) {
        return null;
        // return PetService.getPetsByIds(petIds);
    }

}