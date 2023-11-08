package com.example.allourtrees;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UserDataController {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    String userName;
    String userID;

    public UserDataController (){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        updateData();

    }

    public void updateData(){
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Map<String, Object> data = document.getData();
                        userName = data.get("name").toString();

                        Log.d("MARIA", "DocumentSnapshot data: " + document.getData());


                    } else {
                        Log.d("MARIA", "No such document");
                    }
                } else {
                    Log.d("MARIA", "get failed with ", task.getException());
                }
            }
        });
        }


    public String getUserName() {
        updateData();
        return userName;
    }
}



