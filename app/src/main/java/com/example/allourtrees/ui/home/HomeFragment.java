package com.example.allourtrees.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.allourtrees.MainActivity;
import com.example.allourtrees.UserDataController;
import com.example.allourtrees.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView textView;
    MainActivity activity;

    @Override
    public void onStart() {
        super.onStart();
        activity = (MainActivity) getActivity();
        String name = activity.userDataController.getUserName();
        textView.setText("Hello there " + name);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        textView  = binding.textHome;





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}