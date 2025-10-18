import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserStoreService } from 'src/app/helpers/user-store.service';
import  {jwtDecode, JwtPayload } from 'jwt-decode';
import { Login } from 'src/app/models/login.model';
import { AuthUser } from 'src/app/models/auth-user';

interface CustomJwtPayload extends JwtPayload {
  sub: string;
  email: string;
  role: string;
  username: string;
  userId: number;
  name: string; // Adding the name to the custom payload
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private userStore: UserStoreService
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  login(): void {
    if (this.loginForm.valid) {
      const loginData: Login = {
        email: this.loginForm.value.username,
        password: this.loginForm.value.password
      };
      console.log("loginData", loginData);
      this.authService.login(loginData).subscribe({
        next: (tokenDto: { jwtToken: string }) => {
          const token = tokenDto.jwtToken;
          console.log("Received token:", token);
          this.authService.setToken(token);
          const user = this.decodeAndStoreToken(token);
          this.redirectBasedOnRole(user.role);
        },
        error: (err) => {
          this.errorMessage = "Invalid Credentials! Please Try Again.";
          console.log("Login Error", err);
        }
      });
    }
  }

  private decodeAndStoreToken(token: string): AuthUser {
    const decodedToken = jwtDecode<CustomJwtPayload>(token);
    const user: AuthUser = {
      userId: decodedToken.userId,
      userEmail: decodedToken.email,
      jwtToken: token,
      role: decodedToken.role,
      name: decodedToken.name // Storing the name from the token
    };
    this.userStore.setUser(user);
    return user;
  }

  private redirectBasedOnRole(userRole: string): void {
    if (userRole === 'ROLE_ADMIN') {
      this.router.navigate(['/addfeedback']);
    } else if (userRole === 'ROLE_PETOWNER') {
      this.router.navigate(['/home']);
    } else {
      this.router.navigate(['/home']);
    }
  }

  get f() {
    return this.loginForm.controls;
  }
}