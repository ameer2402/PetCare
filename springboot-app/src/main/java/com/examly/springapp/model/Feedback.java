package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entity annotation indicates that this class is mapped to a database table.
 */
@Entity
public class Feedback {

    /**
     * Primary key for the Feedback entity.
     * Annotated with `@Id` to specify the primary key.
     * `@GeneratedValue` annotation is used for auto-generating the primary key
     * value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    /**
     * Many-to-one relationship with User entity.
     * 
     * @JoinColumn specifies the foreign key column.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;

    /**
     * Many-to-one relationship with Appointment entity.
     * 
     * @JoinColumn specifies the foreign key column.
     */
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    @NotNull(message = "Appointment is required")
    private Appointment appointment;

    /**
     * Field representing feedback message.
     */
    @NotBlank(message = "Message is required")
    @Size(max = 500, message = "Message must be less than 500 characters")
    private String message;

    /**
     * Field representing feedback rating.
     */
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    /**
     * Default constructor for Feedback class.
     */
    public Feedback() {
    }

    /**
     * Parameterized constructor for Feedback class.
     *
     * @param feedbackId  The ID of the feedback.
     * @param appointment The appointment associated with the feedback.
     * @param message     The feedback message.
     * @param rating      The feedback rating.
     */
    public Feedback(int feedbackId, Appointment appointment, String message, int rating) {
        this.feedbackId = feedbackId;
        this.appointment = appointment;
        this.message = message;
        this.rating = rating;
    }

    /**
     * Getter and setter methods for feedbackId.
     */
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
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
     * Getter and setter methods for appointment.
     */
    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    /**
     * Getter and setter methods for message.
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter and setter methods for rating.
     */
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}