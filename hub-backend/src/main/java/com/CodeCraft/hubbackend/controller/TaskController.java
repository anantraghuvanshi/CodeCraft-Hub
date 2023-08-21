package com.CodeCraft.hubbackend.controller;

import com.CodeCraft.hubbackend.model.DashboardInsights;
import com.CodeCraft.hubbackend.model.Task;
import com.CodeCraft.hubbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) throws ExecutionException, InterruptedException {
        Task createdTask = taskService.saveTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAll() throws ExecutionException, InterruptedException {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) throws ExecutionException, InterruptedException {
        Task task = taskService.getTaskById(id);
        return (task != null) ? new ResponseEntity<>(task, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable String id, @RequestBody Task task) throws ExecutionException, InterruptedException {
        String response = taskService.updateTask(id, task);
        return "Task updated successfully".equals(response) ? new ResponseEntity<>(response, HttpStatus.OK) : new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        String response = taskService.deleteTask(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*----- DashBoard Insights Mapping -----*/
    @GetMapping("/dashboard-insights")
    public DashboardInsights getDashboardInsights() throws ExecutionException, InterruptedException {
        return taskService.getDashBoardInsights();
    }

    /*----- Task timer -----*/
    @PutMapping("/start-task/{id}")
    public Task startTask(@PathVariable String id) throws ExecutionException, InterruptedException {
        return taskService.startTask(id);
    }

    @PutMapping("/stop-task/{id}")
    public Task stopTask(@PathVariable String id) throws ExecutionException, InterruptedException {
        return taskService.stopTask(id);
    }
    @PutMapping("/resume-task/{id}")
    public Task resumeTask(@PathVariable String id) throws ExecutionException, InterruptedException {
        return taskService.resumeTask(id);
    }
}
