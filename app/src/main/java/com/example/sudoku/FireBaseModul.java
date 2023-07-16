package com.example.sudoku;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireBaseModul {

    private FirebaseStorage firebaseFireStore;// = FirebaseStorage.getInstance();

    private FirebaseFirestore firestore;// = FirebaseFirestore.getInstance();


    private Map<String, String> userdata;// = new HashMap<>();


    // Create a storage reference from our app
//    private StorageReference storageRef = firebaseFireStore.getReference();
//
//    //user Id's
//    private StorageReference userIdRef = storageRef.child("users");
//
//    private String path = userIdRef.getPath();
//
//    //time(score)
//    private StorageReference userTime = userIdRef.child("time");
//
//    //users time
//    private StorageReference userIdTimeRef = storageRef.child("users/time");

    private static FireBaseModul fireBaseModul = null;
    public static void init(){
        fireBaseModul = new FireBaseModul();
    }

    private FireBaseModul() {
        this.firebaseFireStore =  FirebaseStorage.getInstance();
        this.firestore = FirebaseFirestore.getInstance();;
        this.userdata = new HashMap<>();
    }

    public static FireBaseModul getFireBaseModul(){
        return fireBaseModul;
    }

    public void uploadUser(LocalUser userTime) {

        userdata.put("ID", userTime.getUserID());
        userdata.put("Name", userTime.getUserNickName());
        userdata.put("Time", userTime.getUserTime());

        firestore.collection("users")
                .document(userTime.getUserID())
                .set(userdata)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("ASD", "uploaded");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ASD", "failed");
            }
        });




    }

    public void readUserData(){


        firestore.collection("users")
                .document(userdata.get("ID"))
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userdata.put("Time",documentSnapshot.getString("Time"));
                userdata.put("Name",documentSnapshot.getString("Name"));

                Log.d("ASD", "read from server " + userdata.get("Name"));
                Log.d("ASD", "read from server " + userdata.get("Time"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ASD", "failed");
            }
        });
        Log.d("ASD", "after read " + userdata.get("Time"));


    }

    public void readAllUsers(){

        // Specify the collection reference
        CollectionReference collectionRef = firestore.collection("users");

// Define the batch size
        int batchSize = 10;

// Define the starting point for the query (null for the initial request)
        DocumentSnapshot lastVisibleDocument = null;

// Perform the query to retrieve the batch of documents
        Query query = collectionRef.orderBy("Time", Query.Direction.ASCENDING).limit(batchSize);
        if (lastVisibleDocument != null) {
            query = query.startAfter(lastVisibleDocument);
        }

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                        // Iterate over the documents
                        for (DocumentSnapshot document : documents) {
                            // Access the data of each document
                            Map<String, Object> data = document.getData();
                            // Process the data as needed
                        }

                        // Check if there are more documents to fetch
                        if (documents.size() >= batchSize) {
                            // Set the last visible document for the next batch
                         //   lastVisibleDocument = documents.get(documents.size() - 1);
                            // Trigger the next batch retrieval (e.g., by user request)
                            // You can call a method or update UI elements to fetch the next batch
                        } else {
                            // Handle the case when all documents have been retrieved
                        }
                    } else {
                        // Handle the case when there are no documents in the collection
                    }
                } else {
                    // Handle any errors that occurred during the query
                }
            }
        });


    }
}
