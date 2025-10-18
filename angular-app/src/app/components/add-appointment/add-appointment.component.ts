import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router'; // Import ActivatedRoute and Router
import { Appointment } from '../../models/appointment.model';
import { AppointmentService } from '../../services/appointment.service';
import { Pet } from '../../models/pet.model';
import { PetService } from '../../services/pet.service';
import { UserStoreService } from '../../helpers/user-store.service';
 
@Component({
  selector: 'app-add-appointment',
  templateUrl: './add-appointment.component.html',
  styleUrls: ['./add-appointment.component.css']
})
export class AddAppointmentComponent implements OnInit {
 
  addAppointmentForm: FormGroup;
  petId: number;
  pets: Pet[] = [];
  userId: number;
  appointmentId: number;
  isUpdateMode: boolean = false;
 
  constructor(
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly route: ActivatedRoute, // Add ActivatedRoute in the constructor
    private readonly appointmentService: AppointmentService,
    private readonly petService: PetService,
    private readonly userStorageService: UserStoreService
  ) {}
 
  ngOnInit(): void {
    // Get the logged-in user's ID from userStorageService
    const user = JSON.parse(localStorage.getItem('authUser'));
    this.userId = user?.userId;
 
    // Initialize the form with form controls and validators
    this.addAppointmentForm = this.fb.group({
      appointmentDate: ['', Validators.required],
      reason: ['', [Validators.required, Validators.minLength(10)]],
      petId: ['', Validators.required],
    });
 
    // Load the pets for the logged-in user
    this.loadPets();
 
    // Check if the route contains an appointmentId
    this.route.params.subscribe(params => {
      this.appointmentId = +params['appointmentId'];
      if (this.appointmentId) {
        this.isUpdateMode = true;
        this.loadAppointment();
      }
    });
  }
 
  /**
   * Load the pets registered by the logged-in user.
   */
  loadPets(): void {
    this.petService.getPetsByUserId(this.userId).subscribe(
      pets => {
        this.pets = pets;
      },
      error => {
        console.error('Error loading pets', error);
      }
    );
  }
 
  /**
   * Load the appointment for updating.
   */
  loadAppointment(): void {
    this.appointmentService.getAppointmentById(this.appointmentId).subscribe(
      appointment => {
        this.addAppointmentForm.patchValue(appointment);
      },
      error => {
        console.error('Error loading appointment', error);
      }
    );
  }
 
  /**
   * Submit the form to add or update an appointment.
   */
  onSubmit(): void {
    if (this.addAppointmentForm.valid) {
      // Create a new appointment object with the desired format
      const appointmentData = {
        appointmentDate: this.addAppointmentForm.value.appointmentDate,
        reason: this.addAppointmentForm.value.reason,
        user: {
          userId: this.userId
        },
        pet: {
          petId: this.addAppointmentForm.value.petId
        },
        status: 'PENDING' // Set status as PENDING
      };
 
      if (this.isUpdateMode) {
        this.appointmentService.updateAppointment(this.appointmentId, appointmentData).subscribe(
          response => {
            // Show success alert on successful update of appointment
            alert('Appointment updated successfully!');
            // Navigate to the view-appointments page
            this.router.navigate(['/viewappointments']);
          },
          error => {
            // Show error alert if there was an issue updating the appointment
            alert('Error updating appointment: ' + error.message);
          }
        );
      } else {
        this.appointmentService.addAppointment(appointmentData).subscribe(
          response => {
            // Show success alert on successful addition of appointment
            alert('Appointment added successfully!');
            // Navigate to the view-appointments page
            this.router.navigate(['/viewappointments']);
          },
          error => {
            // Show error alert if there was an issue adding the appointment
            alert('Error adding appointment: ' + error.message);
          }
        );
      }
    } else {
      // Show alert if the form is invalid
      alert('Form is invalid');
    }
  }
}