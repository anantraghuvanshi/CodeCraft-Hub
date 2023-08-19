import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
}
