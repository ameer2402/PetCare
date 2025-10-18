
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Feedback } from '../models/feedback.model';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
 
  
  private readonly baseUrl="https://8080-fadffcfdbddbdfbdfacfcddfbedbebb.premiumproject.examly.io/api/feedback";
  constructor(private httpClient: HttpClient) { }


  createFeedback(feedback :Feedback):Observable<Feedback>{
    return this.httpClient.post<Feedback>(this.baseUrl,feedback);
  }

  getAllFeedback() :Observable<Feedback[]>{
    return this.httpClient.get<Feedback[]>(this.baseUrl);
  }

  getAllFeedbackByUserId(userId: number): Observable<Feedback[]> {
    return this.httpClient.get<Feedback[]>(`${this.baseUrl}/${userId}`);
}

  
}
