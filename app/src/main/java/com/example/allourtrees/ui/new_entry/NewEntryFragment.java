package com.example.allourtrees.ui.new_entry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.allourtrees.MainActivity;
import com.example.allourtrees.R;
import com.example.allourtrees.databinding.ActivityMainBinding;
import com.example.allourtrees.databinding.FragmentNewEntryBinding;
import com.example.allourtrees.ui.learn.LearnViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewEntryFragment extends Fragment implements View.OnClickListener {

    private FragmentNewEntryBinding binding;

    BottomNavigationView bottomBar;

    public NewEntryFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewEntryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //bottomBar.setOnClickListener(this);

        return root;
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