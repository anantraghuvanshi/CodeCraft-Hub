import { Component, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TaskService } from 'src/app/services/taskService/task.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';

@Component({
  selector: 'app-task-create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class TaskCreateComponent {
  categories: string[] = ['Coding', 'Research', 'Learning'];
  filteredOptions!: Observable<string[]>;
  taskForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      category: ['', Validators.required],
      deadline: [''],
      status: ['', Validators.required],
    });
    this.filteredOptions = this.taskForm.get('category')!.valueChanges.pipe(
      startWith(''),
      map((value) => this._filter(value))
    );
  }

  onSubmit(): void {
    if (this.taskForm.valid) {
      this.taskService.createTask(this.taskForm.value).subscribe(
        () => {
          this.snackBar.open('Task Created Successfully!', 'Close', {
            duration: 3000,
          });
          this.router.navigate(['/task-list']);
        },
        (error) => {
          this.snackBar.open('Error creating task!', 'Close', {
            duration: 3000,
          });
        }
      );
    }
  }
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    const filteredList = this.categories.filter((option) =>
      option.toLowerCase().includes(filterValue)
    );

    if (filteredList.length === 0) {
      filteredList.push(value);
    }

    return filteredList;
  }
}
