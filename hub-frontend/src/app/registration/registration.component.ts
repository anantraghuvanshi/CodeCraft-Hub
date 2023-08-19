import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/authService/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent {
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.registerForm = this.fb.group({
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe(
        (response) => {
          console.log('Success Response:', response);
          this.snackBar.open(
            'Registration successful! Please login.',
            'Close',
            {
              duration: 3000,
            }
          );
          this.router.navigate(['/login']);
        },
        (error) => {
          console.log('Error Response:', error);
          const errorMsg =
            error && error.error && error.error.message
              ? error.error.message
              : 'There was an issue with the registration. Please try again.';
          this.snackBar.open(errorMsg, 'Close', {
            duration: 5000,
          });
        }
      );
    }
  }
}
