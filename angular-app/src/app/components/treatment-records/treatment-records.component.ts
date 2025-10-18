import { Component, OnInit } from '@angular/core';
import { Appointment } from 'src/app/models/appointment.model';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PetService } from 'src/app/services/pet.service';
import { Router } from '@angular/router'; // Import Router for navigation

@Component({
  selector: 'app-treatment-records',
  templateUrl: './treatment-records.component.html',
  styleUrls: ['./treatment-records.component.css']
})
export class TreatmentRecordsComponent implements OnInit {
  appointments: Appointment[] = [];
  userId: number;

  constructor(
    private appointmentService: AppointmentService,
    private petService: PetService,
    private router: Router // Add Router to constructor
  ) {}

  ngOnInit(): void {
    // Get the logged-in user's ID from localStorage
    const user = JSON.parse(localStorage.getItem('authUser'));
    this.userId = user?.userId;

    // Load appointments for the logged-in user
    this.loadAppointments();
  }

  /**
   * Load appointments for the logged-in user.
   */
  loadAppointments(): void {
    this.appointmentService.getAppointmentsByUserId(this.userId).subscribe(
      appointments => {
        this.appointments = appointments.filter(appointment => appointment.status === 'CLOSED');
        console.log('Loaded appointments:', this.appointments);
      },
      error => {
        console.error('Error loading appointments', error);
      }
    );
  }

  addFeedback(appointmentId: number): void {
    console.log('Add feedback for appointment ID:', appointmentId);
    // Store the appointmentId in localStorage
    localStorage.setItem('currentAppointmentId', appointmentId.toString());
    // Navigate to the feedback form with the appointmentId
    this.router.navigate(['/addfeedback']);
  }
}