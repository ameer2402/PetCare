
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.url.includes('/login')) {
      return next.handle(request);
    }
    console.log('Intercepting request:', request.url);
    const token = sessionStorage.getItem('authToken'); // Change this line to use sessionStorage
    console.log('Token:', token);
    if (token) {
      const authReq = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      console.log('Request with Authorization header:', authReq);
      return next.handle(authReq);
    }
    return next.handle(request);
  }
}