import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment'; // If you're using environment variables.

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private baseUrl = `${environment.apiUrl}/api/tasks`; // Assuming your API is structured like this.

  constructor(private http: HttpClient) {}

  getDashboardInsights(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/dashboard-insights`);
  }
}
