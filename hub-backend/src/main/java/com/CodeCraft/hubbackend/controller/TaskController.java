package com.CodeCraft.hubbackend.controller;

import com.CodeCraft.hubbackend.model.Task;
import com.CodeCraft.hubbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) throws ExecutionException, InterruptedException{
        return taskService.saveTask(task);
    }
    @GetMapping("/all")
    public List<Task> getAll() throws ExecutionException, InterruptedException{
        return taskService.getAllTasks();
    }
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) throws ExecutionException, InterruptedException{
        return taskService.getTaskById(id);
    }
    @PutMapping("/update/{id}")
    public String updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable String id){
        return taskService.deleteTask(id);
    }
}
