package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.examly.springapp.exception.PetNotFoundException;
import com.examly.springapp.exception.UserNotFoundException;
import com.examly.springapp.model.Appointment;
import com.examly.springapp.model.Pet;
import com.examly.springapp.repository.AppointmentRepo;
import com.examly.springapp.repository.FeedbackRepo;
import com.examly.springapp.repository.PetRepo;

import jakarta.transaction.Transactional;

/**
 * Implementation of the PetService interface, providing methods to handle
 * pet-related operations such as adding, retrieving, updating, and deleting
 * pets.
 */
@Service
public class PetServiceImpl implements PetService {

    private final PetRepo petRepo;
    private final AppointmentRepo appointmentRepo;
    private final FeedbackRepo feedbackRepo;

    public PetServiceImpl(PetRepo petRepo, AppointmentRepo appointmentRepo, FeedbackRepo feedbackRepo) {
        this.petRepo = petRepo;
        this.appointmentRepo = appointmentRepo;
        this.feedbackRepo = feedbackRepo;
    }

    @Override
    public Pet addPet(Pet pet) {
        return petRepo.save(pet);
    }

    @Override
    public Pet getPetById(int petId) throws PetNotFoundException {
        return petRepo.findById(petId).orElse(null);
    }

    @Override
    public Pet updatePetById(int petId, Pet pet) throws PetNotFoundException {
        Pet existingPet = petRepo.findById(petId).orElse(null);
        if (existingPet != null) {
            existingPet.setName(pet.getName());
            existingPet.setSpecies(pet.getSpecies());
            existingPet.setBreed(pet.getBreed());
            existingPet.setDateOfBirth(pet.getDateOfBirth());
            existingPet.setUser(pet.getUser());
            existingPet.setStatus(pet.getStatus());
            return petRepo.save(existingPet);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deletePetById(int petId) throws PetNotFoundException {
        Pet pet = petRepo.findById(petId).orElse(null);
        if (pet != null) {
            List<Appointment> appointments = appointmentRepo.findByPetPetId(petId);
            for (Appointment appointment : appointments) {
                feedbackRepo.deleteByAppointmentAppointmentId(appointment.getAppointmentId()); // Delete associated
                                                                                               // feedbacks
            }
            appointmentRepo.deleteAll(appointments); // Delete associated appointments
            petRepo.deleteById(petId); // Delete the pet
            return true;
        }
        return false;
    }

    @Override
    public List<Pet> getAllPetsByUserId(int userId) throws UserNotFoundException {
        return petRepo.findAllPetsByUserId(userId);
    }

    @Override
    public String getPetNameById(Integer petId) {
        // Implement this method if necessary
        return null;
    }
}