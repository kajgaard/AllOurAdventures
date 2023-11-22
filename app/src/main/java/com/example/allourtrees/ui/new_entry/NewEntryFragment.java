package com.example.allourtrees.ui.new_entry;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.allourtrees.Adventure;
import com.example.allourtrees.Attraction;
import com.example.allourtrees.MainActivity;
import com.example.allourtrees.R;
import com.example.allourtrees.databinding.ActivityMainBinding;
import com.example.allourtrees.databinding.FragmentNewEntryBinding;
import com.example.allourtrees.ui.learn.LearnViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewEntryFragment extends Fragment implements View.OnClickListener {

    private FragmentNewEntryBinding binding;

    AutoCompleteTextView attractionField;
    List<String> listOfAttractionNames = new ArrayList<>();
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    EditText dateField;
    EditText notesField;

    FloatingActionButton saveAdventureBtn;

    Adventure thisAdventure;

    boolean isFragmentActive;


    public NewEntryFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewEntryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<Attraction> attractionList = new ArrayList<>();
        attractionList = populateAttractionListFromCsv();
        attractionList.forEach(a -> addOnlyNameToList(a));
        attractionField = binding.attractionET;
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listOfAttractionNames);
        attractionField.setThreshold(0);
        attractionField.setAdapter(adapter);
        notesField = binding.notesET;
        dateField = binding.dateET;
        saveAdventureBtn = getActivity().findViewById(R.id.fab);
        saveAdventureBtn.setOnClickListener(this);
        isFragmentActive = true;


        return root;
    }


    public void addOnlyNameToList(Attraction attraction){
        listOfAttractionNames.add(attraction.getAttractionName());
    }
    private List<Attraction> populateAttractionListFromCsv(){
        return(((MainActivity)getActivity()).getAttractionList());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFragmentActive = false;
        binding = null;
    }

    @Override
    public void onClick(View view) {
       if(view == saveAdventureBtn && isFragmentActive){

           if(!attractionField.getText().toString().equals("")) {
               thisAdventure = createAdventure();
               Log.e("BUTTON", "I am frag, and should close");
               ((MainActivity)getActivity()).navController.popBackStack();
               ((MainActivity)getActivity()).navController.navigate(R.id.navigation_home);
               saveAdventure();
               Toast.makeText(getActivity(), "Adventure saved!", Toast.LENGTH_SHORT).show();
               MainActivity.updateAlreadyVisitedAttractions();
           }else {
               Toast.makeText(getActivity(), "Nothing was entered, returning to home", Toast.LENGTH_SHORT).show();
               ((MainActivity)getActivity()).navController.popBackStack();
               ((MainActivity)getActivity()).navController.navigate(R.id.navigation_home);
           }
       }
    }

    public void saveAdventure(){    // Saving the brew to FireStore, history collection

        thisAdventure = createAdventure();

        Map<String,Object> newAdventure = new HashMap<>();
        newAdventure.put("attractionName", thisAdventure.attractionName);
        newAdventure.put("visitDate", thisAdventure.visitDate);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        fStore.collection("users").document(userID).collection("adventures").document().set(newAdventure);

        //// Formatting the date to wanted format, but we dont do that currently
        //date = new Date();
        //dateName = binding.dateET;
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN);
        //dateToDB = formatter.format(date);
        //Log.i("date",formatter.format(date));

    }


    public Adventure createAdventure(){
        Adventure adventure;
        if(notesField.getText() == null) {
            adventure = new Adventure(attractionField.getText().toString(), dateField.getText().toString());
        }else{
            adventure = new Adventure(attractionField.getText().toString(), dateField.getText().toString(),notesField.getText().toString());
        }
        return adventure;
    }

}