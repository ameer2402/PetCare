package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exception.FeedbackAlreadyExistException;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller class to handle feedback-related operations.
 */
@RestController
@RequestMapping("api/feedback")
@Tag(name = "Feedback", description = "Operations pertaining to feedback in Pet Care Application")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * Constructor injection for FeedbackService.
     *
     * @param feedbackService The service to handle feedback operations.
     */
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * HTTP GET endpoint to retrieve all feedback.
     *
     * @return A list of all feedback.
     */
    @Operation(summary = "Get all feedback", description = "Retrieve a list of all feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "No feedback found")
    })
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Feedback>> getAll() {
        List<Feedback> getAllFeedback = feedbackService.getAllFeedbacks();
        if (getAllFeedback.isEmpty()) {
            // Return HTTP 404 if no feedback is found
            return ResponseEntity.status(400).build();
        }
        // Return HTTP 200 and the list of feedback if found
        return ResponseEntity.status(200).body(getAllFeedback);
    }

    /**
     * HTTP POST endpoint to add new feedback.
     *
     * @param feedback The feedback to be added.
     * @return The created feedback.
     */
    @Operation(summary = "Add new feedback", description = "Submit new feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feedback successfully added"),
            @ApiResponse(responseCode = "404", description = "Feedback not added")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    @PostMapping
    public ResponseEntity<Feedback> addFeedBack(@Valid @RequestBody Feedback feedback)
            throws FeedbackAlreadyExistException {
        Feedback feedbacks = feedbackService.addFeedback(feedback);
        if (feedbacks == null) {
            // Return HTTP 404 if feedback addition fails
            return ResponseEntity.status(404).body(null);
        }
        // Return HTTP 201 if feedback is added successfully
        return ResponseEntity.status(201).body(feedbacks);
    }

    /**
     * HTTP DELETE endpoint to delete feedback by its ID.
     *
     * @param feedbackId The ID of the feedback to be deleted.
     * @return A message indicating the deletion status.
     */
    @Operation(summary = "Delete feedback", description = "Delete an existing feedback by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<String> deleteFeedBack(@PathVariable int feedbackId) {
        boolean deleteFeed = feedbackService.deleteFeedbackByFeedbackId(feedbackId);
        if (deleteFeed) {
            // Return HTTP 200 if feedback is deleted successfully
            return ResponseEntity.status(200).body("Deleted FeedBack of Id " + feedbackId + " successfully.");
        }
        // Return HTTP 404 if feedback is not found
        return ResponseEntity.status(404).body("Deleted FeedBack of Id " + feedbackId + " not found to delete.");
    }

    /**
     * HTTP GET endpoint to retrieve feedback by user ID.
     *
     * @param userId The ID of the user whose feedback is to be retrieved.
     * @return A list of feedback for the specified user.
     */
    @Operation(summary = "Get feedback by user ID", description = "Retrieve feedback by a specific user's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feedback successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Feedback not found")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    // @PostAuthorize("returnObject.stream().allMatch(feedback ->
    // feedback.getUser().getEmail() == authentication.name) ")
    @GetMapping("/{userId}")
    public ResponseEntity<List<Feedback>> getFeedBackByuser(@PathVariable int userId) {
        List<Feedback> feedback = feedbackService.getFeedbacksByUserId(userId);
        if (feedback == null) {
            // Return HTTP 404 if feedback for the user is not found
            return ResponseEntity.status(404).body(null);
        }
        // Return HTTP 200 and the list of feedback if found
        return ResponseEntity.status(200).body(feedback);
    }
}