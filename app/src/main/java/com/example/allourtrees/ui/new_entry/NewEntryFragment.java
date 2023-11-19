package com.example.allourtrees.ui.new_entry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.allourtrees.Attraction;
import com.example.allourtrees.MainActivity;
import com.example.allourtrees.R;
import com.example.allourtrees.databinding.ActivityMainBinding;
import com.example.allourtrees.databinding.FragmentNewEntryBinding;
import com.example.allourtrees.ui.learn.LearnViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class NewEntryFragment extends Fragment implements View.OnClickListener {

    private FragmentNewEntryBinding binding;

    BottomNavigationView bottomBar;

    AutoCompleteTextView attractionField;
    List<String> listOfAttractionNames = new ArrayList<>();


    public NewEntryFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewEntryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //bottomBar.setOnClickListener(this);

       //val forerliste = resources.getStringArray(R.array.fartojsforer)
       //val adapterSkipper = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, forerliste)
       //fartoj_autoCompleteTextView.setAdapter(adapterSkipper)


        List<Attraction> attractionList = new ArrayList<>();
        attractionList = populateAttractionListFromCsv();
        attractionList.forEach(a -> addOnlyNameToList(a));
        attractionField = binding.attractionET;
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listOfAttractionNames);
        attractionField.setThreshold(1);
        attractionField.setAdapter(adapter);



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
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if(view == bottomBar){
            Toast.makeText(getActivity(), "Trying to close fragment", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(this).commit();
        }
    }
}