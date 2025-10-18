package com.examly.springapp.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

/**
 * Entity annotation indicates that this class is mapped to a database table.
 */
@Entity
public class Pet {

    /**
     * Primary key for the Pet entity.
     * Annotated with `@Id` to specify the primary key.
     * `@GeneratedValue` annotation is used for auto-generating the primary key
     * value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;

    /**
     * Fields representing pet information.
     */
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Species is required")
    @Size(max = 100, message = "Species must be less than 100 characters")
    private String species;

    @NotBlank(message = "Breed is required")
    @Size(max = 100, message = "Breed must be less than 100 characters")
    private String breed;

    @NotNull(message = "Date of Birth is required")
    @Past(message = "Date of Birth must be in the past")
    private LocalDateTime dateOfBirth;

    /**
     * Many-to-one relationship with User entity.
     * 
     * @JoinColumn specifies the foreign key column.
     */
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    @NotNull(message = "User is required")
    private User user;

    @NotBlank(message = "Status is required")
    private String status = "healthy";

    /**
     * Default constructor for Pet class.
     */
    public Pet() {
    }

    /**
     * Parameterized constructor for Pet class.
     *
     * @param petId       The ID of the pet.
     * @param name        The name of the pet.
     * @param species     The species of the pet.
     * @param breed       The breed of the pet.
     * @param dateOfBirth The date of birth of the pet.
     * @param user        The user associated with the pet.
     * @param status      The status of the pet.
     */
    public Pet(int petId, String name, String species, String breed, LocalDateTime dateOfBirth, User user,
            String status) {
        this.petId = petId;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
        this.status = status;
    }

    /**
     * Getter and setter methods for petId.
     */
    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    /**
     * Getter and setter methods for name.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter and setter methods for species.
     */
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * Getter and setter methods for breed.
     */
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * Getter and setter methods for dateOfBirth.
     */
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Getter and setter methods for user.
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter and setter methods for status.
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}