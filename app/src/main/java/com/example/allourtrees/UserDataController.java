package com.example.allourtrees;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDataController {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    String userName;
    String userID;

    public  ArrayList<String> visitedAttractions = new ArrayList<>();
    public  ArrayList<String> visitedAttractionsDates = new ArrayList<>();

    // Static variable reference of single_instance
    // of type Singleton
    private static UserDataController single_instance = null;

    private UserDataController (){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        getUserNameFromDB(valueList -> Log.e("FIREBASE","Username from DB: "+valueList.toString()));

    }

    public static synchronized UserDataController getInstance(){
        if (single_instance == null)
            single_instance = new UserDataController();

        return single_instance;
    }

    public String getUserName() {
        return userName;
    }

    public void getAlreadyVisitedAttractionsFromDB(MyFirebaseCallBack firebaseCallBack){

        FirebaseFirestore fStore;
        fStore = FirebaseFirestore.getInstance();
        visitedAttractions.clear();
        visitedAttractionsDates.clear();

        Query query = fStore.collection("users").document(mAuth.getUid()).collection("adventures");  // without ordering

        query
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            visitedAttractions.add((String) document.get("attractionName").toString());
                            visitedAttractionsDates.add((String) document.get("visitDate").toString());
                            ArrayList list = new ArrayList<>();
                            firebaseCallBack.onCallback(list);
                            Log.w("GETDB", document.getId() + " => " + document.getData());
                        }

                    } else {
                        Log.w("GETDB", "Error getting documents: ", task.getException());
                    }
                });

    }
    public void getUserNameFromDB(MyFirebaseCallBack firebaseCallBack){
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Map<String, Object> data = document.getData();
                        userName = data.get("name").toString();
                        ArrayList list = new ArrayList<>();
                        list.add(userName);
                        firebaseCallBack.onCallback(list);

                    } else {
                        Log.d("MARIA", "No such document");
                    }
                } else {
                    Log.d("MARIA", "get failed with ", task.getException());
                }
            }
        });
    }

    public  ArrayList<String> getVisitedAttractions() {
        return visitedAttractions;
    }

    public  ArrayList<String> getVisitedAttractionsDates() {
        return visitedAttractionsDates;
    }

    public interface MyFirebaseCallBack {

        void onCallback(List<String> valueList);

    }

}







