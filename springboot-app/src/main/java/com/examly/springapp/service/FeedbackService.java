package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.exception.FeedbackAlreadyExistException;
import com.examly.springapp.model.Feedback;

/**
 * Service interface for handling feedback-related operations.
 */
public interface FeedbackService {

    /**
     * Retrieves all feedbacks.
     * 
     * @return A list of all feedbacks
     */

    List<Feedback> getAllFeedbacks();

    /**
     * Adds new feedback.
     * 
     * @param feedback The feedback to be added
     * @return The added feedback
     */
    Feedback addFeedback(Feedback feedback) throws FeedbackAlreadyExistException;

    /**
     * Retrieves feedbacks by user ID.
     * 
     * @param userId The ID of the user whose feedbacks are to be retrieved
     * @return A list of feedbacks belonging to the specified user
     */
    List<Feedback> getFeedbacksByUserId(int userId);

    /**
     * Deletes feedback by feedback ID.
     * 
     * @param feedbackId The ID of the feedback to be deleted
     * @return true if the feedback was successfully deleted, false otherwise
     */
    boolean deleteFeedbackByFeedbackId(int feedbackId);

}
