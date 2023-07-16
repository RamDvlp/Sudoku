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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FireBaseModul {

    private FirebaseStorage firebaseFireStore;// = FirebaseStorage.getInstance();

    private FirebaseFirestore firestore;// = FirebaseFirestore.getInstance();


    private Map<String, String> userdata;// = new HashMap<>();

    private Completion_Time_Callback completion_time_callback;


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
                completion_time_callback.setUserScore("And you " +userdata.get("Name") + " finished the sudoku in " + userdata.get("Time"));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ASD", "failed");
            }
        });




    }

    public void setCompletion_time_callback(Completion_Time_Callback completion_time_callback){
        this.completion_time_callback = completion_time_callback;
    }

    private String messege;

    public void readUserData(){

        String userID;
        userID = mySP.getSP().readID();
        if(userID.equals(""))
            return;

        firestore.collection("users")
                .document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                userdata.put("Time",documentSnapshot.getString("Time"));
                userdata.put("Name",documentSnapshot.getString("Name"));
                messege = "And you " +userdata.get("Name") + " finished the sudoku in " + userdata.get("Time");

                if(userdata.get("Time") != null){
                    completion_time_callback.setUserScore(messege);

                }else {
                    messege = "No Score Set YET!";
                    completion_time_callback.setUserScore(messege);


                }

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


    // Define the starting point for the query (null for the initial request)
    private DocumentSnapshot lastVisibleDocument = null;

    public ArrayList<String> readAllUsers(){


        String userID;
        userID = mySP.getSP().readID();

        ArrayList<String> returnedData = new ArrayList<>();
        // Specify the collection reference
        CollectionReference collectionRef = firestore.collection("users");

        // Define the batch size
        int batchSize = 10;

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
                            userdata.put("ID", String.valueOf(data.get("ID")));
                            userdata.put("Name", String.valueOf(data.get("Name")));
                            userdata.put("Time", String.valueOf(data.get("Time")));

                            //pretty print preperation
                            int lenspace = userdata.get("Name").length();
                            String space = "";
                            while(20 - lenspace >0){
                                space += " ";
                                lenspace++;
                            }


                            returnedData.add(returnedData.size()+1 + " | " +userdata.get("Name") + space + userdata.get("Time"));
//
//                            if(userdata.get("ID").equals(userID)){
//                                completion_time_callback.setUserScore(messege);
//                            }

                        }

                        // Check if there are more documents to fetch
                        if (documents.size() >= batchSize) {
                            // Set the last visible document for the next batch
                            lastVisibleDocument = documents.get(documents.size() - 1);
                            // Trigger the next batch retrieval (e.g., by user request)
                            // You can call a method or update UI elements to fetch the next batch
                        } else {
                            // Handle the case when all documents have been retrieved
                            //completion_time_callback.setUserScore("No Score Set YET!");

                            //return;
                        }
                    } else {
                        // Handle the case when there are no documents in the collection
                        //return;
                    }
                } else {
                    // Handle any errors that occurred during the query
                    //return;
                }
            }
        });


        return returnedData;
    }
//
//    public ArrayList<String> getAllUsers(){
//
//
//        ArrayList<String> returnedData = new ArrayList<>();
//        // Specify the collection reference
//        CollectionReference collectionRef = firestore.collection("users");
//
//// Define the batch size
//        int batchSize = 10;
//
//// Perfo
//
//        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        String name = document.getString("Name");
//                        String time = document.getString("Time");
//                        String uid = document.getId();
//                        returnedData.add(name + "   " + time);
//                        //DocumentReference uidRef = firestore.collection("users").document(uid);
//                    }
//
//        }
//            }
//        });
//        return returnedData;
//    }
}
