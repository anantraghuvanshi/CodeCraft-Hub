import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/authService/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    this.loginForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe(
        (response: any) => {
          const successMsg = response.message || 'Logged in successfully';
          this.snackBar.open(successMsg, 'Close', { duration: 3000 });
          this.router.navigate(['/dashboard']);
        },
        (error) => {
          const errorMsg =
            error && error.error && error.error.message
              ? error.error.message
              : 'There was an issue with the login. Please try again.';
          this.snackBar.open(errorMsg, 'Close', { duration: 5000 });
        }
      );
    }
  }
}
