
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddPetsComponent } from './components/add-pets/add-pets.component';
import { ViewPetComponent } from './components/view-pet/view-pet.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/register/register.component';
import { ViewAllFeedbackComponent } from './components/view-all-feedback/view-all-feedback.component';
import { AddFeedbackComponent } from './components/add-feedback/add-feedback.component';
import { ViewAllAppointmentsComponent } from './components/view-all-appointments/view-all-appointments.component';
import { ViewAppointmentsComponent } from './components/view-appointments/view-appointments.component';
import { ViewAppointmentsRecordsComponent } from './components/view-appointments-records/view-appointments-records.component';
import { TreatmentRecordsComponent } from './components/treatment-records/treatment-records.component';
import { ViewMyFeedbackComponent } from './components/view-my-feedback/view-my-feedback.component';
import { AddAppointmentComponent } from './components/add-appointment/add-appointment.component';
 
const routes: Routes = [
  {path:'home' , component:HomeComponent},
  {path:'register', component:RegisterComponent},
  {path:'feedbacklist',component:ViewAllFeedbackComponent},
  {path:'addappointment',component:AddAppointmentComponent},
  {path:'viewallappointmentrecords', component:ViewAppointmentsRecordsComponent},
  {path:'addfeedback',component:AddFeedbackComponent},
  {path:'viewmyfeedback',component:ViewMyFeedbackComponent},
  {path:'login',component:LoginComponent},
  {path: 'addpet', component: AddPetsComponent},
  {path: 'viewpet', component: ViewPetComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path:'treatmentrecords', component:TreatmentRecordsComponent},
  {path:'viewappointments',component:ViewAppointmentsComponent},
  {path:'viewallappointments',component:ViewAllAppointmentsComponent},
  {path:'addappointment/:appointmentId', component:AddAppointmentComponent},
  {path: 'addpet/:petId', component: AddPetsComponent},
 
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
 