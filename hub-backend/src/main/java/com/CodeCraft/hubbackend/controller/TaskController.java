package com.CodeCraft.hubbackend.controller;

import com.CodeCraft.hubbackend.model.DashboardInsights;
import com.CodeCraft.hubbackend.model.Task;
import com.CodeCraft.hubbackend.model.User;
import com.CodeCraft.hubbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal User currentUser) throws ExecutionException, InterruptedException {
        return taskService.saveTask(task, currentUser.getId());
    }

    @GetMapping("/all")
    public List<Task> getAll(@AuthenticationPrincipal User currentUser) throws ExecutionException, InterruptedException {
        return taskService.getAllTasksByUserId(currentUser.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id, @AuthenticationPrincipal User currentUser) throws ExecutionException, InterruptedException {
        Task task = taskService.getTaskById(id, currentUser.getId());
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable String id, @RequestBody Task task, @AuthenticationPrincipal User currentUser) throws ExecutionException, InterruptedException {
        String response = taskService.updateTask(id, task, currentUser.getId());
        if ("Task updated successfully".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id, @AuthenticationPrincipal User currentUser) throws ExecutionException, InterruptedException {
        String response = taskService.deleteTask(id, currentUser.getId());
        if ("Task deleted successfully".equals(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


    /*----- DashBoard Insights Mapping -----*/
    @GetMapping("/dashboard-insights")
    public DashboardInsights getDashboardInsights(@AuthenticationPrincipal User currentUser) throws ExecutionException, InterruptedException {
        return taskService.getDashBoardInsights(currentUser.getId());
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
