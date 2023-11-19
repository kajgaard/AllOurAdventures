package com.example.allourtrees.ui.learn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allourtrees.Attraction;
import com.example.allourtrees.AttractionsAdapter;
import com.example.allourtrees.MainActivity;
import com.example.allourtrees.databinding.FragmentLearnBinding;

import java.util.List;

public class LearnFragment extends Fragment {

    private FragmentLearnBinding binding;

    private RecyclerView attractionRecyclerView;

    private AttractionsAdapter attractionsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LearnViewModel learnViewModel =
                new ViewModelProvider(this).get(LearnViewModel.class);

        binding = FragmentLearnBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //badgeList = generateBadgeItems();
        //
        //        //badgeRecyclerView = rootView.findViewById(R.id.badges_recyclerview); //From tutorial, but using binding is easier :))
        //        badgeRecyclerView = binding.badgesRecyclerview;
        //        LinearLayoutManager layoutManager
        //                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //        badgeRecyclerView.setLayoutManager(layoutManager);
        //        badgeAdapter = new BadgeAdapter(badgeList);
        //        badgeRecyclerView.setAdapter(badgeAdapter);

        attractionRecyclerView = binding.attractionsRecyclerview;
        LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        attractionRecyclerView.setLayoutManager(layoutManager);
        attractionsAdapter = new AttractionsAdapter(populateAttractionListFromCsv());
        attractionRecyclerView.setAdapter(attractionsAdapter);


        return root;
    }

    private List<Attraction> populateAttractionListFromCsv(){
        return(((MainActivity)getActivity()).getAttractionList());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}