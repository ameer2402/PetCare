package com.examly.springapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.AppointmentExceedException;
import com.examly.springapp.exception.AppointmentNotFoundException;
import com.examly.springapp.model.Appointment;
import com.examly.springapp.model.Pet;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.AppointmentRepo;
import com.examly.springapp.repository.PetRepo;
import com.examly.springapp.repository.UserRepo;

import jakarta.transaction.Transactional;

/**
 * Implementation of the AppointmentService interface, providing methods to
 * handle
 * appointment-related operations such as adding, retrieving, updating, and
 * deleting appointments.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepo appointmentRepo;

    private final UserRepo userRepo;
    private final PetRepo petRepo;
    private static final int MAX_APPOINTMENTS = 15;

    /**
     * Constructor to initialize AppointmentServiceImpl with the necessary
     * dependency.
     *
     * @param appointmentRepo Repository for appointment data access operations
     */
    public AppointmentServiceImpl(AppointmentRepo appointmentRepo, UserRepo userRepo, PetRepo petRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
        this.petRepo = petRepo;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(int userId) {
        return appointmentRepo.findByUser_UserId(userId);
    }

    /**
     * Adds a new appointment to the repository.
     * 
     * @param appointment The appointment to be added
     * @return The added appointment
     */
    @Override
    public Appointment addAppointment(Appointment appointment)
            throws AppointmentExceedException, AppointmentNotFoundException {
        Optional<User> user = userRepo.findById(appointment.getUser().getUserId());
        if (!user.isPresent()) {
            throw new AppointmentNotFoundException("User not found!");
        }

        Optional<Pet> pet = petRepo.findById(appointment.getPet().getPetId());
        if (!pet.isPresent()) {
            throw new AppointmentNotFoundException("Pet not found!");
        }

        LocalDateTime startOfDay = appointment.getAppointmentDate().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        int totalAppointments = appointmentRepo.countByAppointmentDateBetween(startOfDay, endOfDay);

        if (totalAppointments >= MAX_APPOINTMENTS) {
            throw new AppointmentExceedException(
                    "Cannot add more than " + MAX_APPOINTMENTS + " appointments within the specified day.");
        }

        return appointmentRepo.save(appointment);
    }

    /**
     * Retrieves an appointment by its ID.
     * 
     * @param appointmentId The ID of the appointment to be retrieved
     * @return The retrieved appointment, or null if not found
     */
    @Override
    public Appointment getAppointmentById(int appointmentId) throws AppointmentNotFoundException {
        return appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found !"));
    }

    /**
     * Updates an appointment by its ID. If the appointment with the given ID is
     * found, its details are updated.
     * 
     * @param appointmentId The ID of the appointment to be updated
     * @param appointment   The updated appointment details
     * @return The updated appointment, or null if not found
     * @throws AppointmentNotFoundException
     */
    @Override
    public Appointment updateAppointmentById(int appointmentId, Appointment appointment)
            throws AppointmentNotFoundException {
        Optional<Appointment> existingAppointment = appointmentRepo.findById(appointmentId);
        if (existingAppointment.isPresent()) {
            Appointment updatedAppointment = existingAppointment.get();
            updatedAppointment.setAppointmentDate(appointment.getAppointmentDate());
            updatedAppointment.setReason(appointment.getReason());
            updatedAppointment.setUser(appointment.getUser());
            updatedAppointment.setPet(appointment.getPet());
            updatedAppointment.setStatus(appointment.getStatus());
            return appointmentRepo.save(updatedAppointment);
        } else {
            throw new AppointmentNotFoundException("Appointment not found !"); // throw an exception if appointment not
                                                                               // found
        }
    }

    /**
     * Deletes an appointment by its ID. If the appointment with the given ID is
     * found, it is deleted.
     * 
     * @param appointmentId The ID of the appointment to be deleted
     * @return true if the appointment was successfully deleted, false otherwise
     * @throws AppointmentNotFoundException
     */
    @Override
    public boolean deleteAppointmentById(int appointmentId) throws AppointmentNotFoundException {
        if (appointmentRepo.existsById(appointmentId)) {
            appointmentRepo.deleteById(appointmentId);
            return true;
        } else {
            throw new AppointmentNotFoundException("Appointment not found !"); // throw an exception if appointment not
                                                                               // found
        }
    }

    @Override
    public void updateAppointmentStatus(int appointmentId, String status) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment ID: " + appointmentId));
        appointment.setStatus(status);
        appointmentRepo.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointmentsWithPet() {
        return appointmentRepo.findAllWithPet();
    }

    @Override
    public Optional<Pet> getPetByAppointmentId(int appointmentId) {
        Optional<Appointment> appointment = appointmentRepo.findById(appointmentId);
        if (appointment.isPresent()) {
            return Optional.of(appointment.get().getPet());
        }
        return Optional.empty();
    }
}