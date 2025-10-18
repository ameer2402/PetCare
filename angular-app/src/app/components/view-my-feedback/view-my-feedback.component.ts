import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-view-my-feedback',
  templateUrl: './view-my-feedback.component.html',
  styleUrls: ['./view-my-feedback.component.css']
})
export class ViewMyFeedbackComponent implements OnInit {

  userId: number;
  feedbacks: Feedback[] = [];

  constructor(private feedbackService: FeedbackService) { }

  ngOnInit(): void {
    const authUser = JSON.parse(localStorage.getItem('authUser'));
    this.userId = authUser?.userId;

    if (this.userId) {
      this.getAllFeedbackByUserId();
    }
  }

  getAllFeedbackByUserId(): void {
    this.feedbackService.getAllFeedbackByUserId(this.userId).subscribe((data) => {
      this.feedbacks = data;
      console.log(this.feedbacks); // Verify that data is being fetched correctly
    });
  }
}