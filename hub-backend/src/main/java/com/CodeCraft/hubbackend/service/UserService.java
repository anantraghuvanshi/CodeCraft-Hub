package com.CodeCraft.hubbackend.service;

import com.CodeCraft.hubbackend.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private Firestore dbFirestore = FirestoreClient.getFirestore();

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String createUser(User user) throws ExecutionException, InterruptedException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ApiFuture<WriteResult> future = dbFirestore.collection("users").document(user.getUserName()).set(user);
        return future.get().getUpdateTime().toString();
    }
    public Optional<User> findByUsername(String username) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection("users").document(username);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return Optional.of(document.toObject(User.class));
        } else {
            return Optional.empty();
        }
    }
    public boolean existsByUsername(String username) throws ExecutionException, InterruptedException {
        DocumentReference docRef = dbFirestore.collection("users").document(username);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        return future.get().exists();
    }
    public boolean existsByEmail(String email) throws ExecutionException, InterruptedException {
        CollectionReference users = dbFirestore.collection("users");
        Query query = users.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        return !querySnapshot.get().getDocuments().isEmpty();
    }
}
