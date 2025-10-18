import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { Appointment } from 'src/app/models/appointment.model';
import { Router } from '@angular/router';
 
@Component({
  selector: 'app-view-appointments',
  templateUrl: './view-appointments.component.html',
  styleUrls: ['./view-appointments.component.css']
})
export class ViewAppointmentsComponent implements OnInit {
  appointments: Appointment[] = [];
  userId: number;
 
  constructor(
    private appointmentService: AppointmentService,
    private router: Router
  ) {}
 
  ngOnInit(): void {
    const user = JSON.parse(localStorage.getItem('authUser'));
    this.userId = user?.userId;
    this.loadAppointments();
  }
 
  loadAppointments(): void {
    this.appointmentService.getAppointmentsByUserId(this.userId).subscribe(
      appointments => {
        this.appointments = appointments;
        console.log('Loaded appointments:', this.appointments);
      },
      error => {
        console.error('Error loading appointments', error);
      }
    );
  }
 
  editAppointment(appointment: Appointment): void {
    this.router.navigate(['/addappointment', appointment.appointmentId]);
  }
 
  deleteAppointment(appointmentId: number): void {
    this.appointmentService.deleteAppointment(appointmentId).subscribe(
      () => {
        this.loadAppointments();
      },
      error => {
        console.error('Error deleting appointment', error);
      }
    );
  }
}