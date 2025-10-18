import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddAppointmentComponent } from './components/add-appointment/add-appointment.component';
import { AddFeedbackComponent } from './components/add-feedback/add-feedback.component';
import { AddPetsComponent } from './components/add-pets/add-pets.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { TreatmentRecordsComponent } from './components/treatment-records/treatment-records.component';
import { ViewAllAppointmentsComponent } from './components/view-all-appointments/view-all-appointments.component';
import { ViewAllFeedbackComponent } from './components/view-all-feedback/view-all-feedback.component';
import { ViewAppointmentsComponent } from './components/view-appointments/view-appointments.component';
import { ViewAppointmentsRecordsComponent } from './components/view-appointments-records/view-appointments-records.component';
import { ViewMyRecordsComponent } from './components/view-my-records/view-my-records.component';
import { ViewPetComponent } from './components/view-pet/view-pet.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ErrorComponent } from './components/error/error.component';
import { RegisterComponent } from './components/register/register.component';
import { UserNavComponent } from './components/user-nav/user-nav.component';
import { AdminNavComponent } from './components/admin-nav/admin-nav.component';
import { CommonModule } from '@angular/common';
import { ViewMyFeedbackComponent } from './components/view-my-feedback/view-my-feedback.component';
import { StatusFilterPipe } from './pipes/status-filter.pipe'; // Import StatusFilterPipe
import { AuthInterceptor } from './interceptor/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    AddAppointmentComponent,
    AddFeedbackComponent,
    AddPetsComponent,
    HomeComponent,
    LoginComponent,
    NavbarComponent,
    RegisterComponent,
    TreatmentRecordsComponent,
    ViewAllAppointmentsComponent,
    ViewAllFeedbackComponent,
    ViewAppointmentsComponent,
    ViewAppointmentsRecordsComponent,
    ViewMyRecordsComponent,
    ViewPetComponent,
    ErrorComponent,
    UserNavComponent,
    AdminNavComponent,
    ViewMyFeedbackComponent,
    StatusFilterPipe // Declare StatusFilterPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
