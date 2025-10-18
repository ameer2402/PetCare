/**
 * Interface representing an appointment scheduled between a pet owner and HospitalAdmin.
 */
export interface Appointment {
    /**
     * Unique identifier for the appointment.
     */
    appointmentId?: number;

    /**
     * Unique identifier for the pet.
     */
    petId?: number;

    /**
     * Date and time of the appointment.
     */
    appointmentDate?: string;

    /**
     * Reason for scheduling the appointment.
     */
    reason?: string;

    /**
     * Unique identifier for the user (pet owner).
     */
    userId?: number;

    /**
     * Status of the appointment (e.g., Scheduled, Completed, Canceled).
     */
    status?: string;
}