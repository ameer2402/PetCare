import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registrationForm: FormGroup;
  errorMessage: string = '';

  constructor(private readonly fb: FormBuilder, private readonly authService: AuthService, private readonly router: Router) { 
    this.registrationForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)]],
      confirmPassword: ['', Validators.required],
      mobileNumber: ['', [Validators.required, Validators.pattern(/^[0-9]*$/)]],
      userRole: ['', Validators.required]
    }, { validator: this.passwordMatchValidator });
  }

  ngOnInit(): void {
  }

  passwordMatchValidator(group: FormGroup) {
    return group.get('password')?.value === group.get('confirmPassword')?.value ? null : { mismatch: true };
  }

  register(): void {
    if (this.registrationForm.valid) {
      const { username, email, password, mobileNumber, userRole } = this.registrationForm.value;
      const user: User = { username, email, password, mobileNumber, userRole };
      console.log("form submitted",user);
      this.authService.register(user).subscribe(
        () => {
          alert('Registration successful');
          this.router.navigate(['/login']);
        },
        (error) => {
          console.error('Registration failed', error);
          this.errorMessage = error.error.message || 'Registration failed';
        }
      );
    } 
  }
}