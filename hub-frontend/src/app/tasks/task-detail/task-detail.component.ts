import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Task } from 'src/app/models/task';
import { TaskService } from 'src/app/services/taskService/task.service';

@Component({
  selector: 'app-task-detail',
  templateUrl: './task-detail.component.html',
  styleUrls: ['./task-detail.component.css'],
})
export class TaskDetailComponent {
  task: Task | null = null;

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.fetchTask();
  }

  fetchTask(): void {
    const taskId = this.route.snapshot.paramMap.get('id');
    if (taskId) {
      this.taskService.getTaskById(taskId).subscribe((data: Task) => {
        this.task = data;
      });
    }
  }

  startTask(): void {
    if (this.task?.id) {
      this.taskService.startTask(this.task.id).subscribe(() => {
        this.fetchTask();
      });
    }
  }

  stopTask(): void {
    if (this.task?.id) {
      this.taskService.stopTask(this.task.id).subscribe(() => {
        this.fetchTask();
      });
    }
  }

  resumeTask(): void {
    if (this.task?.id) {
      this.taskService.resumeTask(this.task.id).subscribe(() => {
        this.fetchTask();
      });
    }
  }
}
