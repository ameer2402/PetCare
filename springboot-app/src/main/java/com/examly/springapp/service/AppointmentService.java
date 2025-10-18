package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.exception.AppointmentExceedException;
import com.examly.springapp.exception.AppointmentNotFoundException;
import com.examly.springapp.model.Appointment;
import com.examly.springapp.model.Pet;

/**
 * Service interface for handling appointment-related operations.
 */
public interface AppointmentService {

    /**
     * Retrieves all appointments.
     * 
     * @return A list of all appointments
     */
    List<Appointment> getAllAppointments();

    /**
     * Retrieves appointments by user ID.
     * 
     * @param userId The ID of the user whose appointments are to be retrieved
     * @return A list of appointments belonging to the specified user
     */
    List<Appointment> getAppointmentsByUserId(int userId);

    /**
     * Adds a new appointment.
     * 
     * @param appointment The appointment to be added
     * @return The added appointment
     * @throws AppointmentExceedException
     * @throws AppointmentNotFoundException
     */
    Appointment addAppointment(Appointment appointment) throws AppointmentExceedException, AppointmentNotFoundException;

    /**
     * Retrieves an appointment by its ID.
     * 
     * @param appointmentId The ID of the appointment to be retrieved
     * @return The retrieved appointment, or null if not found
     */
    Appointment getAppointmentById(int appointmentId) throws AppointmentNotFoundException;

    /**
     * Updates an appointment by its ID.
     * 
     * @param appointmentId The ID of the appointment to be updated
     * @param appointment   The updated appointment details
     * @return The updated appointment, or null if not found
     * @throws AppointmentNotFoundException
     */
    Appointment updateAppointmentById(int appointmentId, Appointment appointment) throws AppointmentNotFoundException;

    /**
     * Deletes an appointment by its ID.
     * 
     * @param appointmentId The ID of the appointment to be deleted
     * @return true if the appointment was successfully deleted, false otherwise
     * @throws AppointmentNotFoundException
     */
    boolean deleteAppointmentById(int appointmentId) throws AppointmentNotFoundException;

    public void updateAppointmentStatus(int appointmentId, String status);

    public List<Appointment> getAllAppointmentsWithPet();

    public Optional<Pet> getPetByAppointmentId(int appointmentId);

}