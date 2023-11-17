package com.example.allourtrees.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allourtrees.BadgeAdapter;
import com.example.allourtrees.BadgeItem;
import com.example.allourtrees.MainActivity;
import com.example.allourtrees.R;
import com.example.allourtrees.UserDataController;
import com.example.allourtrees.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView textView;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String userName;
    String userID;

    //////////////////////////////////

    private List<BadgeItem> badgeList;
    private RecyclerView badgeRecyclerView;
    private BadgeAdapter badgeAdapter;

    public HomeFragment(){
        //Required empty constructor (idfk)
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();



        //////////////////////////////////////////////////////////
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false); //From tutorial, but using binding is easier :))

        //textView = rootView.findViewById(R.id.name_of_user_TV); //From tutorial, but using binding is easier :))
        textView = binding.nameOfUserTV;

        badgeList = genreateBadgeItems();

        //badgeRecyclerView = rootView.findViewById(R.id.badges_recyclerview); //From tutorial, but using binding is easier :))
        badgeRecyclerView = binding.badgesRecyclerview;
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        badgeRecyclerView.setLayoutManager(layoutManager);
        badgeAdapter = new BadgeAdapter(badgeList);
        badgeRecyclerView.setAdapter(badgeAdapter);


        updateData();

        //return rootView;

        return root;
    }

    private List<BadgeItem> genreateBadgeItems(){
        //Should be fetched from db
        List<BadgeItem> badgeItems = new ArrayList<>();
        badgeItems.add(new BadgeItem(R.drawable.novice_explorer_badge,"Novice Explorer Badge","Make 50 total discoveries to achieve this bagde",43,50));
        badgeItems.add(new BadgeItem(R.drawable.church_badge,"The Pilgrim Badge","Visit all the 10 danish cathedrals",2,10));
        badgeItems.add(new BadgeItem(R.drawable.troll_badge,"The Trolls of Denmark Badge","Visit all the trolls in Denmark made by the artist Thomas Dambo",7,26));
        return badgeItems;
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
                        textView.setText(""+userName);

                    } else {

                    }
                } else {
                    Log.d("MARIA", "get failed with ", task.getException());
                }
            }
        });
    }

}

