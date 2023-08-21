import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Task } from 'src/app/models/task';
import { TaskService } from 'src/app/services/taskService/task.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css'],
})
export class TaskListComponent {
  tasks: Task[] = [];

  constructor(private taskService: TaskService, private router: Router) {}

  ngOnInit(): void {
    this.fetchAllTasks();
  }

  fetchAllTasks(): void {
    this.taskService.getAllTasks().subscribe((data: Task[]) => {
      this.tasks = data;
    });
  }

  onTaskSelect(taskId: string): void {
    this.router.navigate(['/tasks', taskId]);
  }

  startTask(id: string): void {
    this.taskService.startTask(id).subscribe((task: Task) => {
      if (task && task.id) {
        const index = this.tasks.findIndex((t) => t.id === task.id);
        if (index !== -1) {
          this.tasks[index] = task;
        }
      }
    });
  }

  stopTask(id: string): void {
    this.taskService.stopTask(id).subscribe((task: Task) => {
      if (task && task.id) {
        const index = this.tasks.findIndex((t) => t.id === task.id);
        if (index !== -1) {
          this.tasks[index] = task;
        }
      }
    });
  }

  resumeTask(id: string): void {
    this.taskService.resumeTask(id).subscribe((task: Task) => {
      if (task && task.id) {
        const index = this.tasks.findIndex((t) => t.id === task.id);
        if (index !== -1) {
          this.tasks[index] = task;
        }
      }
    });
  }
}
