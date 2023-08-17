package com.CodeCraft.hubbackend.service;

import com.CodeCraft.hubbackend.model.Task;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class TaskService {

    private Firestore dbFirestore = FirestoreClient.getFirestore();

    public Task saveTask(Task task) throws ExecutionException, InterruptedException {
        CollectionReference tasks = dbFirestore.collection("tasks");
        DocumentReference addedDocRef = tasks.add(task).get();
        task.setId(addedDocRef.getId());
        return task;
    }


}
