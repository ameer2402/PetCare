
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appointment } from '../models/appointment.model';
import { Pet } from '../models/pet.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private apiUrl = 'https://8080-fadffcfdbddbdfbdfacfcddfbedbebb.premiumproject.examly.io/api/appointments';

  constructor(private http: HttpClient) { }

  /**
   * Fetch all appointments from the server.
   */
  getAllAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(this.apiUrl);
  }

  /**
   * Fetch a specific appointment by its ID.
   * @param id - The ID of the appointment to fetch.
   */
  getAppointmentById(id: number): Observable<Appointment> {
    return this.http.get<Appointment>(`${this.apiUrl}/${id}`);
  }

  /**
   * Fetch appointments for a specific user by their user ID.
   * @param userId - The ID of the user whose appointments are to be fetched.
   */
  getAppointmentsByUserId(userId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/user/${userId}`);
  }

  /**
   * Add a new appointment.
   * @param appointment - The appointment to add.
   */
  addAppointment(appointment: Appointment): Observable<Appointment> {
    return this.http.post<Appointment>(this.apiUrl, appointment);
  }

  /**
   * Update an existing appointment.
   * @param id - The ID of the appointment to update.
   * @param appointment - The updated appointment data.
   */
  updateAppointment(id: number, appointment: Appointment): Observable<Appointment> {
    return this.http.put<Appointment>(`${this.apiUrl}/${id}`, appointment);
  }

  /**
   * Fetch pet details by appointment ID.
   * @param id - The ID of the appointment.
   */
  getPetByAppointmentId(id: number): Observable<Pet> {
    return this.http.get<Pet>(`${this.apiUrl}/${id}/pet`);
  }

  /**
   * Update the status of an appointment.
   * @param appointmentId - The ID of the appointment.
   * @param status - The new status.
   */
  updateAppointmentStatus(appointmentId: number, status: string): Observable<void> {
    console.log(status);
    return this.http.put<void>(`${this.apiUrl}/${appointmentId}/status`,  status );
  }

  /**
   * Delete an appointment.
   * @param id - The ID of the appointment to delete.
   */
  deleteAppointment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}