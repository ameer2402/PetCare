package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Appointment;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.AppointmentRepo;
import com.examly.springapp.repository.FeedbackRepo;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.exception.FeedbackAlreadyExistException;

/**
 * Implementation of the FeedbackService interface, providing methods to handle
 * feedback-related operations such as adding, retrieving, and deleting
 * feedbacks.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackRepo feedbackRepo;
    private UserRepo userRepo;
    private AppointmentRepo appointmentRepo;

    /**
     * Constructor to initialize FeedbackServiceImpl with the necessary dependency.
     *
     * @param feedbackRepo Repository for feedback data access operations
     */
    public FeedbackServiceImpl(FeedbackRepo feedbackRepo, UserRepo userRepo, AppointmentRepo appointmentRepo) {
        this.userRepo = userRepo;
        this.feedbackRepo = feedbackRepo;
        this.appointmentRepo = appointmentRepo;
    }

    /**
     * Retrieves all feedbacks from the repository.
     * 
     * @return A list of all feedbacks
     */
    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }

    /**
     * Adds new feedback to the repository.
     * 
     * @param feedback The feedback to be added
     * @return The added feedback
     */
    @Override
    public Feedback addFeedback(Feedback feedback) throws FeedbackAlreadyExistException {

        Optional<User> user = userRepo.findById(feedback.getUser().getUserId());
        // .orElseThrow(() -> new RuntimeException("User not found"));
        Appointment appointment = appointmentRepo.findById(feedback.getAppointment().getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return feedbackRepo.save(feedback);
    }

    /**
     * Retrieves feedbacks by user ID.
     * 
     * @param userId The ID of the user whose feedbacks are to be retrieved
     * @return A list of feedbacks belonging to the specified user
     */
    @Override
    public List<Feedback> getFeedbacksByUserId(int userId) {
        return feedbackRepo.findByUser(userId);
    }

    /**
     * Deletes feedback by feedback ID. If the feedback with the given ID is found,
     * it is deleted.
     * 
     * @param feedbackId The ID of the feedback to be deleted
     * @return true if the feedback was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteFeedbackByFeedbackId(int feedbackId) {
        if (feedbackRepo.existsById(feedbackId)) {
            feedbackRepo.deleteById(feedbackId);
            return true;
        }
        return false;
    }

}