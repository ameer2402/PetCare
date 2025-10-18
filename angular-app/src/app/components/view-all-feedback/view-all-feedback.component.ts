import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-view-all-feedback',
  templateUrl: './view-all-feedback.component.html',
  styleUrls: ['./view-all-feedback.component.css']
})
export class ViewAllFeedbackComponent implements OnInit {

  feedback:Feedback[];

  constructor(private readonly feedbackService:FeedbackService) { }

  ngOnInit(): void {
    this.getFeedback();
  }

  getFeedback(){
    this.feedbackService.getAllFeedback().subscribe((data)=>{
      this.feedback=data;
      console.log(this.feedback);
    })
  }
}