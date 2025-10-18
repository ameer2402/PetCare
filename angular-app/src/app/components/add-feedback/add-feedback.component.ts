import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-add-feedback',
  templateUrl: './add-feedback.component.html',
  styleUrls: ['./add-feedback.component.css']
})
export class AddFeedbackComponent implements OnInit {

  feedbackForm: FormGroup;

  constructor(
    private readonly feedbackService: FeedbackService,
    private readonly formBuilder: FormBuilder,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.feedbackForm = this.formBuilder.group({
      message: ['', [Validators.required, Validators.maxLength(200)]],
      rating: ['', [Validators.required, Validators.max(5), Validators.min(1)]]
    });
  }

  addFeedback(): void {
    if (this.feedbackForm.valid) {
      const authUser = JSON.parse(localStorage.getItem('authUser'));
      const userId = authUser?.userId;
      const appointmentIdStr = localStorage.getItem('currentAppointmentId');
      const appointmentId = appointmentIdStr ? parseInt(appointmentIdStr, 10) : null; // Ensure appointmentId is an integer

      if (appointmentId === null || isNaN(appointmentId)) {
        console.error('Invalid appointment ID');
        return;
      }

      const feedbackData = {
        ...this.feedbackForm.value,
        user: { userId: userId },
        appointment: { appointmentId: appointmentId }
      };

      console.log(feedbackData);
      
      this.feedbackService.createFeedback(feedbackData).subscribe(() => {
        // Clear the appointmentId from localStorage
        localStorage.removeItem('currentAppointmentId');
        
        // Navigate to feedback list
        this.router.navigate(['/viewmyfeedback']);
      });
    }
  }
}