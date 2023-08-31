package com.CodeCraft.hubbackend.service;

import com.CodeCraft.hubbackend.model.DashboardInsights;
import com.CodeCraft.hubbackend.model.Task;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private Firestore dbFirestore = FirestoreClient.getFirestore();

    public Task saveTask(Task task, String userId) throws ExecutionException, InterruptedException {
        task.setUserId(userId);
        CollectionReference tasks = dbFirestore.collection("tasks");
        DocumentReference addedDocRef = tasks.add(task).get();
        task.setId(addedDocRef.getId());
        addedDocRef.set(task).get();
        return task;
    }

    public List<Task> getAllTasksByUserId(String userId) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documents = dbFirestore.collection("tasks").whereEqualTo("userId", userId).get().get().getDocuments();
        List<Task> tasks = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            tasks.add(document.toObject(Task.class));
        }
        return tasks;
    }

    public Task getTaskById(String id, String userId) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = dbFirestore.collection("tasks").document(id).get().get();
        Task task = document.toObject(Task.class);

        if (task != null && task.getUserId().equals(userId)) {
            return task;
        } else {
            return null;
        }
    }


    public String updateTask(String id, Task task, String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection("tasks").document(id);
        Task existingTask = docRef.get().get().toObject(Task.class);

        if (existingTask != null && existingTask.getUserId().equals(userId)) {
            docRef.set(task).get();
            return "Task updated successfully";
        } else {
            return "Task not found or unauthorized";
        }
    }


    public String deleteTask(String id, String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection("tasks").document(id);
        Task existingTask = docRef.get().get().toObject(Task.class);

        if (existingTask != null && existingTask.getUserId().equals(userId)) {
            docRef.delete();
            return "Task deleted successfully";
        } else {
            return "Task not found or unauthorized";
        }
    }


    /*---DashboardInsights-----*/
    public DashboardInsights getDashBoardInsights(String userId) throws ExecutionException, InterruptedException{
        DashboardInsights insights = new DashboardInsights();

        List<Task> allTasks = getAllTasksByUserId(userId);

        insights.setTotalTasks((long) allTasks.size());
        insights.setPendingTasks(allTasks.stream().filter(t ->"pending".equalsIgnoreCase(t.getStatus())).count());
        insights.setCompletedTasks(allTasks.stream().filter(t -> "completed".equalsIgnoreCase(t.getStatus())).count());
        insights.setInProgressTasks(allTasks.stream().filter(t -> "in-progress".equalsIgnoreCase(t.getStatus())).count());

        insights.setUpcomingDeadlines(allTasks.stream().filter(t -> t.getDeadline().after(new Date())).sorted(Comparator.comparing(Task::getDeadline)).limit(5).collect(Collectors.toList()));
        return insights;
    }

    /* ---- Task timer ----*/
    public Task startTask(String id) throws ExecutionException, InterruptedException {
        DocumentReference taskRef = dbFirestore.collection("tasks").document(id);
        DocumentSnapshot document = taskRef.get().get();

        if (document.exists()) {
            Task task = document.toObject(Task.class);
            task.setStartTime(LocalDateTime.now());
            taskRef.set(task).get();
            return task;
        } else {
            return null;
        }
    }

    public Task stopTask(String id) throws ExecutionException, InterruptedException {
        DocumentReference taskRef = dbFirestore.collection("tasks").document(id);
        DocumentSnapshot document = taskRef.get().get();

        if (document.exists()) {
            Task task = document.toObject(Task.class);
            task.setEndTime(LocalDateTime.now());

            Long timeSpent = java.time.Duration.between(task.getStartTime(), task.getEndTime()).toMinutes(); // calculate time spent in minutes
            task.setTotalTimeSpent(task.getTotalTimeSpent() + timeSpent);

            taskRef.set(task);
            return task;
        } else {
            return null;
        }
    }
    public Task resumeTask(String id) throws ExecutionException, InterruptedException {
        DocumentReference taskRef = dbFirestore.collection("tasks").document(id);
        DocumentSnapshot document = taskRef.get().get();

        if (document.exists()) {
            Task task = document.toObject(Task.class);
            task.setStartTime(LocalDateTime.now());
            task.setEndTime(null);

            taskRef.set(task);
            return task;
        } else {
            return null;
        }
    }
}
