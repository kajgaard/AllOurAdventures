package com.example.allourtrees;

import static android.app.PendingIntent.getActivity;


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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserDataController {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    String userName;
    String userID;


    public static ArrayList<String> visitedAttractions = new ArrayList<>();
    public static ArrayList<String> visitedAttractionsDates = new ArrayList<>();

    List<Attraction> allNotVisitedAttractionsAsObjects = new ArrayList<>();

    public List<Attraction> attractionList = new ArrayList<>();

    public List<Attraction> getAttractionList() {
        return attractionList;
    }

    // Static variable reference of single_instance
    // of type Singleton
    private static UserDataController single_instance = null;

    private UserDataController (){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        getUserNameFromDB(valueList -> Log.e("FIREBASE","Username from DB: "+valueList.toString()));
        getAttractionsFromCsv();
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


        visitedAttractions.clear();
        visitedAttractionsDates.clear();

        Query query = db.collection("users").document(mAuth.getUid()).collection("adventures");  // without ordering

        query
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            visitedAttractions.add((String) document.get("attractionName").toString());
                            visitedAttractionsDates.add((String) document.get("visitDate").toString());


                            Log.w("GETDB", document.getId() + " => " + document.getData());
                        }
                        ArrayList list = new ArrayList<>();
                        firebaseCallBack.onCallback(list);

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

    public ArrayList<Attraction> getVisitedAttractionsAsObjects() {
        List<Attraction> allAttractions = null;
        allAttractions = this.attractionList;
        ArrayList<Attraction> allVisitedAttractionsAsObjects = new ArrayList<>();

        for (String visit : this.getVisitedAttractions()) {

            for (Attraction attraction : allAttractions) {

                if (attraction.getAttractionName().equals(visit)) {
                    Log.w("BADGESS", "Found match in list for:" + visit);
                    allVisitedAttractionsAsObjects.add(attraction);
                }
            }
        }

        return allVisitedAttractionsAsObjects;
    }

    private void updateNotVisitedList(){
        this.allNotVisitedAttractionsAsObjects.clear();
        this.allNotVisitedAttractionsAsObjects = this.attractionList;
        for (Attraction attraction : this.attractionList){
            this.allNotVisitedAttractionsAsObjects.remove(attraction);
        }
    }

    public List<Attraction> getAllNotVisitedAttractionsAsObjects() {

        List<Attraction> allAttractions = null;
        allAttractions = this.attractionList;
        List<Attraction> allNotVisitedAttractionsAsObjects = new ArrayList<>();
        allNotVisitedAttractionsAsObjects.addAll(allAttractions);

            for (Attraction attraction : allAttractions) {

                for (String visit : this.getVisitedAttractions()) {

                if (attraction.getAttractionName().equals(visit)) {
                    Log.w("BADGESS", "Found match in list for:" + visit);
                    allNotVisitedAttractionsAsObjects.remove(attraction);
                }
            }
        }

        return allNotVisitedAttractionsAsObjects;
    }

    public interface MyFirebaseCallBack {

        void onCallback(List<String> valueList);

    }


    public void getAttractionsFromCsv(){
        InputStream is = App.getContext().getResources().openRawResource(R.raw.attractions_new_11);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {

                //Split by ';'
                String[] tokens = line.split(";");

                //Read the data
                //for wiki data
                Attraction attraction = new Attraction(Double.valueOf(tokens[4]), Double.valueOf(tokens[5]), tokens[0].replace("_", " "), tokens[1], tokens[2].replaceAll("\\n", ""), Integer.parseInt(tokens[7]), fromStringToArray(tokens[3]), Integer.parseInt(tokens[6]));

                //for demo data
                //Attraction attraction = new Attraction(Double.valueOf(tokens[0]), Double.valueOf(tokens[1]), tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), fromStringToArray(tokens[6]), Integer.parseInt(tokens[7]));
                attractionList.add(attraction);

                Log.w("MARIA", "Just created: " + attraction);
            }
        }catch (IOException e){
            Log.wtf("FileInput", "Error handling data file on line: " + line, e);
        }

    }

    public ArrayList<String> fromStringToArray(String starterString){

        // Step 1: Remove square brackets and single quotes
        String cleanedString = starterString.replaceAll("[\\[\\]']", "");

        // Step 2: Split the string into an array using commas as a delimiter
        String[] elements = cleanedString.split(",\\s*");

        // Step 3: Create an ArrayList and add elements from the array
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(elements));

        // Print the ArrayList
        System.out.println(arrayList);
        return arrayList;
    }

}







