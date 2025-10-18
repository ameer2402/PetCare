
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/user.model';
import { map, tap } from 'rxjs/operators';
import { Login } from '../models/login.model';
import { UserStoreService } from '../helpers/user-store.service';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = "https://8080-fadffcfdbddbdfbdfacfcddfbedbebb.premiumproject.examly.io/api";
  private tokenKey = 'authToken';

  constructor(private http: HttpClient, private userStore: UserStoreService) { }

  register(user: User): Observable<User> {
    return this.http.post<User>(this.baseUrl + "/register", user).pipe(
      tap(registeredUser => {
        console.log("User registered successfully", registeredUser);
      })
    );
  }

  login(credentials: Login): Observable<{ jwtToken: string }> {
    console.log("credentials", credentials);
    return this.http.post<{ jwtToken: string }>(this.baseUrl + "/login", credentials).pipe(
      tap(tokenDto => {
        console.log("Received token:", tokenDto);
      })
    );
  }
  

  setToken(token: string): void {
    sessionStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return sessionStorage.getItem(this.tokenKey);
  }

  decodeToken(token: string): any {
    try {
      console.log("Decoding token:", token);
      if (typeof token !== 'string' || !token) {
        throw new Error("Invalid token specified: must be a string");
      }
      return jwtDecode(token);
    } catch (error) {
      console.error("Error decoding token:", error);
      return null;
    }
  }

  logout(): void {
    this.userStore.setUser(null);
    sessionStorage.removeItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    const user = this.decodeToken(this.getToken() || '');
    return user?.role === 'ADMIN';
  }

  getCurrentUserId(): number | null {
    const user = this.decodeToken(this.getToken() || '');
    return user ? user.userId : null;
  }

  getCustomerName(): string | null {
    const user = this.decodeToken(this.getToken() || '');
    return user?.userEmail;
  }

  getUserRole(): Observable<string | null> {
    return this.userStore.user$.pipe(
      map(user => user?.role || null)
    );
  }
}