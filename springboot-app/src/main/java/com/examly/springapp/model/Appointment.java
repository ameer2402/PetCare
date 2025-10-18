package com.examly.springapp.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entity annotation indicates that this class is mapped to a database table.
 */
@Entity
public class Appointment {

    /**
     * Primary key for the Appointment entity.
     * Annotated with `@Id` to specify the primary key.
     * `@GeneratedValue` annotation is used for auto-generating the primary key
     * value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;

    /**
     * Fields representing appointment information.
     */
    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Reason is required")
    @Size(max = 255, message = "Reason must be less than 255 characters")
    private String reason;

    /**
     * Many-to-one relationship with User entity.
     * 
     * @JoinColumn specifies the foreign key column.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Many-to-one relationship with Pet entity.
     * 
     * @JoinColumn specifies the foreign key column.
     */
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Pattern(regexp = "^(PENDING|APPROVED|REJECTED|CLOSED)$", message = "Status must be either PENDING, APPROVED,CLOSED or REJECTED")
    @NotNull(message = "Status is required")
    private String status = "PENDING"; // Set default status to PENDING

    /**
     * Default constructor for Appointment class.
     */
    public Appointment() {
    }

    /**
     * Parameterized constructor for Appointment class.
     *
     * @param appointmentId   The ID of the appointment.
     * @param appointmentDate The date and time of the appointment.
     * @param reason          The reason for the appointment.
     * @param user            The user associated with the appointment.
     * @param pet             The pet associated with the appointment.
     * @param status          The status of the appointment.
     */
    public Appointment(int appointmentId, LocalDateTime appointmentDate, String reason, User user, Pet pet,
            String status) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
        this.user = user;
        this.pet = pet;
        this.status = status;
    }

    /**
     * Getter and setter methods for appointmentId.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Getter and setter methods for appointmentDate.
     */
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Getter and setter methods for reason.
     */
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
     * Getter and setter methods for pet.
     */
    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
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