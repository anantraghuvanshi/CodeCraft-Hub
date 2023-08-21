import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  private apiUrl = `${environment.apiUrl}/api/auth`;

  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, userData, {
      responseType: 'text',
    });
  }

  login(userData: any): Observable<any> {
    return this.http
      .post<{ userId: string }>(`${this.apiUrl}/login`, userData)
      .pipe(
        tap((response) => {
          if (response && response.userId) {
            localStorage.setItem('loggedInUserId', response.userId);
          }
        })
      );
  }

  logout(): void {
    // Optionally, you can have an API call to invalidate session/token
    localStorage.removeItem('loggedInUserId');
  }
}
