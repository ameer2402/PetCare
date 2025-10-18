package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.examly.springapp.model.Feedback;

/**
 * Repository interface for Feedback entity.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

    /**
     * Custom query to retrieve feedback entries associated with a specific user ID.
     *
     * @param userId the ID of the user whose feedback entries are to be retrieved.
     * @return a list of feedback entries associated with the specified user ID.
     */
    @Query("SELECT f FROM Feedback f WHERE f.user.userId = :userId")
    List<Feedback> findByUser(int userId);

    void deleteByAppointmentAppointmentId(int appointmentId);
}