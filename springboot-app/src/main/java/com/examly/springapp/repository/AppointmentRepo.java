package com.examly.springapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.examly.springapp.model.Appointment;

/**
 * Repository interface for Appointment entity.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    // Custom query to retrieve all appointments associated with a specific user ID
    List<Appointment> findByUser_UserId(int userId);

    List<Appointment> findByPetPetId(int petId);

    @Query("SELECT a FROM Appointment a JOIN FETCH a.pet")
    List<Appointment> findAllWithPet();

    // Custom query to count appointments within a specific time range
    int countByAppointmentDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

}