package com.examly.springapp.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exception.AppointmentExceedException;
import com.examly.springapp.exception.AppointmentNotFoundException;
import com.examly.springapp.model.Appointment;
import com.examly.springapp.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controller class to handle appointment-related operations.
 */

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointments", description = "Operations pertaining to appointments in Pet Care Application")
public class AppointmentController {

    private final AppointmentService appointmentService;

    // Constructor injection
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * HTTP POST endpoint to add a new appointment.
     *
     * @param appointment The appointment to be added.
     * @return The created appointment.
     * @throws AppointmentExceedException If the number of appointments exceeds the
     *                                    limit.
     */
    @Operation(summary = "Add a new appointment", description = "Save a new appointment for a pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment successfully added"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    // @PreAuthorize("hasRole('PETOWNER')")
    // @PostAuthorize("returnObject.user.email == authentication.name or
    // hasRole('ADMIN')")
    @PostMapping
    public Appointment addAppointment(@Valid @RequestBody Appointment appointment)
            throws AppointmentExceedException, AppointmentNotFoundException {
        return appointmentService.addAppointment(appointment);
    }

    @Operation(summary = "Get appointments by user ID", description = "Retrieve a list of appointments for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    // @PreAuthorize("hasRole('PETOWNER')")
    // @PostAuthorize("returnObject.stream().allMatch(appointment ->
    // appointment.getUser().getEmail() == authentication.name) or
    // hasRole('ADMIN')")

    @GetMapping("/user/{userId}")
    public List<Appointment> getAppointmentsByUserId(@PathVariable int userId) {
        return appointmentService.getAppointmentsByUserId(userId);
    }

    @Operation(summary = "Get all appointments", description = "Retrieve a list of all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments successfully retrieved")
    })
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @PostAuthorize("returnObject.user.email == authentication.name")
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    /**
     * HTTP GET endpoint to retrieve an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to be retrieved.
     * @return The appointment with the specified ID.
     * @throws AppointmentNotFoundException If the appointment is not found.
     */

    @Operation(summary = "Get appointment by ID", description = "Retrieve details of an appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    // @PostAuthorize("returnObject.user.email == authentication.name ")
    @GetMapping("/{appointmentId}")
    public Appointment getAppointmentById(@PathVariable int appointmentId) throws AppointmentNotFoundException {
        return appointmentService.getAppointmentById(appointmentId);
    }

    /**
     * HTTP PUT endpoint to update an existing appointment.
     *
     * @param appointmentId The ID of the appointment to be updated.
     * @param appointment   The updated appointment details.
     * @return The updated appointment.
     * @throws AppointmentNotFoundException If the appointment is not found.
     */

    @Operation(summary = "Update an appointment", description = "Update the details of an existing appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment successfully updated"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER') or hasRole('ROLE_ADMIN')")
    // @PostAuthorize("returnObject.user.email == authentication.name")
    @PutMapping("/{appointmentId}")

    public Appointment updateAppointment(@PathVariable int appointmentId, @Valid @RequestBody Appointment appointment)
            throws AppointmentNotFoundException {
        return appointmentService.updateAppointmentById(appointmentId, appointment);
    }

    /**
     * HTTP DELETE endpoint to delete an existing appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to be deleted.
     * @return True if the appointment was successfully deleted.
     * @throws AppointmentNotFoundException If the appointment is not found.
     */

    @Operation(summary = "Delete an appointment", description = "Delete an existing appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    // @PreAuthorize("hasRole('ROLE_PETOWNER')")
    // @PostAuthorize("returnObject.user.email == authentication.name")
    @DeleteMapping("/{appointmentId}")
    public boolean deleteAppointment(@PathVariable int appointmentId) throws AppointmentNotFoundException {
        return appointmentService.deleteAppointmentById(appointmentId);
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<Void> updateAppointmentStatus(@PathVariable int appointmentId, @RequestBody String status) {
        appointmentService.updateAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok().build();
    }
}