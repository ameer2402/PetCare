import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PetService } from 'src/app/services/pet.service';

@Component({
  selector: 'app-view-appointments-records',
  templateUrl: './view-appointments-records.component.html',
  styleUrls: ['./view-appointments-records.component.css']
})
export class ViewAppointmentsRecordsComponent implements OnInit {
  closedAppointments: any[] = [];

  constructor(
    private appointmentService: AppointmentService,
    private petService: PetService,
  ) {}

  ngOnInit(): void {
    // Load all closed appointments
    this.loadClosedAppointments();
  }

  /**
   * Load all closed appointments.
   */
  loadClosedAppointments(): void {
    this.appointmentService.getAllAppointments().subscribe(
      appointments => {
        console.log(appointments);
        this.closedAppointments = appointments.filter(appointment => appointment.status === 'CLOSED' || appointment.status === 'REJECTED');
        console.log(this.closedAppointments);
        console.log('Loaded closed appointments:', this.closedAppointments);
      },
      error => {
        console.error('Error loading appointments', error);
      }
    );
  }

  getPetName(petId: number): string {
    let petName = '';
    // this.petService.getPetNameById(petId).subscribe(
    //   name => {
    //     petName = name;
    //   },
    //   error => {
    //     console.error('Error fetching pet name', error);
    //   }
    // );
    return petName;
  }

  getPetOwnerName(userId: number): string {
    let ownerName = '';
    // this.userService.getUserNameById(userId).subscribe(
    //   name => {
    //     ownerName = name;
    //   },
    //   error => {
    //     console.error('Error fetching owner name', error);
    //   }
    // );
    return ownerName;
  }




}