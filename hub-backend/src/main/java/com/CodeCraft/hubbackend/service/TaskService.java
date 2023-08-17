package com.CodeCraft.hubbackend.service;

import com.CodeCraft.hubbackend.model.DashboardInsights;
import com.CodeCraft.hubbackend.model.Task;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private Firestore dbFirestore = FirestoreClient.getFirestore();

    public Task saveTask(Task task) throws ExecutionException, InterruptedException {
        CollectionReference tasks = dbFirestore.collection("tasks");
        DocumentReference addedDocRef = tasks.add(task).get();
        task.setId(addedDocRef.getId());
        addedDocRef.set(task);
        return task;
    }
    public List<Task> getAllTasks() throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documents = dbFirestore.collection("tasks").get().get().getDocuments();
        List<Task> tasks = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            tasks.add(document.toObject(Task.class));
        }
        return tasks;
    }
    public Task getTaskById(String id) throws ExecutionException, InterruptedException{
        DocumentSnapshot document = dbFirestore.collection("tasks").document(id).get().get();
        return document.toObject(Task.class);
    }
    public String updateTask(String id, Task task){
        dbFirestore.collection("tasks").document(id).set(task);
        return "Task updated successfully";
    }
    public String deleteTask(String id){
        dbFirestore.collection("tasks").document(id).delete();
        return "Task deleted successfully";
    }

    /*---DashboardInsights-----*/
    public DashboardInsights getDashBoardInsights() throws ExecutionException, InterruptedException{
        DashboardInsights insights = new DashboardInsights();

        List<Task> allTasks = getAllTasks();

        insights.setTotalTasks((long) allTasks.size());
        insights.setPendingTasks(allTasks.stream().filter(t ->"pending".equalsIgnoreCase(t.getStatus())).count());
        insights.setCompletedTasks(allTasks.stream().filter(t -> "completed".equalsIgnoreCase(t.getStatus())).count());
        insights.setInProgressTasks(allTasks.stream().filter(t -> "in-progress".equalsIgnoreCase(t.getStatus())).count());

        insights.setUpcomingDeadlines(allTasks.stream().filter(t -> t.getDeadline().after(new Date())).sorted(Comparator.comparing(Task::getDeadline)).limit(5).collect(Collectors.toList()));
        return insights;
    }
}
