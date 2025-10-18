import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-view-all-appointments',
  templateUrl: './view-all-appointments.component.html',
  styleUrls: ['./view-all-appointments.component.css']
})
export class ViewAllAppointmentsComponent implements OnInit {
  appointments: any[] = [];

  constructor(
    private appointmentService: AppointmentService,
    private petService: PetService
  ) {}

  ngOnInit(): void {
    // Load all appointments
    this.loadAppointments();
  }

  /**
   * Load all appointments.
   */
  loadAppointments(): void {
    this.appointmentService.getAllAppointments().subscribe(
      appointments => {
        this.appointments = appointments;
        console.log('Loaded appointments:', this.appointments);
      },
      error => {
        console.error('Error loading appointments', error);
      }
    );
  }



  confirmAppointment(appointmentId: number): void {
    console.log('Confirm appointment ID:', appointmentId);
    this.updateAppointmentStatus(appointmentId, 'APPROVED');
  }

  rejectAppointment(appointmentId: number): void {
    console.log('Reject appointment ID:', appointmentId);
    this.updateAppointmentStatus(appointmentId, 'REJECTED');
  }

  closeAppointment(appointmentId: number): void {
    console.log('Close appointment ID:', appointmentId);
    this.updateAppointmentStatus(appointmentId, 'CLOSED');
  }

  updateAppointmentStatus(appointmentId: number, status: string): void {
    this.appointmentService.updateAppointmentStatus(appointmentId, status).subscribe(
      () => {
        console.log(`Appointment ${appointmentId} status updated to ${status}`);
        this.loadAppointments(); // Reload appointments to reflect the status change
      },
      error => {
        console.error(`Error updating appointment status to ${status}:`, error);
      }
    );
  }
}