import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private apiUrl = `${environment.apiUrl}/api/tasks`;

  constructor(private http: HttpClient) {}

  getDashboardInsights(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/dashboard-insights`);
  }
}
