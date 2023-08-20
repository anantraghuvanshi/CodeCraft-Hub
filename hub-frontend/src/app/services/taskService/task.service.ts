import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Task } from 'src/app/models/task';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  constructor(private http: HttpClient) {}

  // Tasks list, detail, create
  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${environment.apiUrl}/api/tasks/all`);
  }

  getTaskById(id: string): Observable<Task> {
    return this.http.get<Task>(`${environment.apiUrl}/api/tasks/${id}`);
  }
  createTask(task: Task): Observable<Task> {
    return this.http.post<Task>(`${environment.apiUrl}/api/tasks/create`, task);
  }
  private handleError(error: any) {
    console.error('Server error:', error);
    if (error.error instanceof Error) {
      const errMsg = error.error.message;
      return throwError(errMsg);
    }
    return throwError(error || 'Server error');
  }
  // Task timer

  startTask(id: String): Observable<Task> {
    return this.http.get<Task>(
      `${environment.apiUrl}/api/tasks/start-task/${id}`
    );
  }
  stopTask(id: String): Observable<Task> {
    return this.http.get<Task>(
      `${environment.apiUrl}/api/tasks/stop-task/${id}`
    );
  }
  resumeTask(id: String): Observable<Task> {
    return this.http.get<Task>(
      `${environment.apiUrl}/api/tasks/resume-task/${id}`
    );
  }
}
