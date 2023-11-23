package com.example.allourtrees.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allourtrees.BadgeAdapter;
import com.example.allourtrees.BadgeItem;
import com.example.allourtrees.CommunityActivityItem;
import com.example.allourtrees.CommunityAdapter;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    TextView textView;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String userName;
    String userID;

    int numberOfAdventuresAllTime = 0;
    int numberOfAttractionsThisWeek = 0;
    int numberOfAttractionsThisMonth = 0;
    int numberOfAttractionsThisYear = 0;

    //////////////////////////////////

    private List<BadgeItem> badgeList;
    private RecyclerView badgeRecyclerView;
    private BadgeAdapter badgeAdapter;


    private List<CommunityActivityItem> communityActivityItemList;
    private RecyclerView communityRecyclerView;
    private CommunityAdapter communityAdapter;
    UserDataController userDataController = UserDataController.getInstance();

    TextView statAllTime, statWeek, statMonth, statYear;

    public HomeFragment(){
        //Required empty constructor (idfk)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        textView.setText("");
        userDataController.getUserNameFromDB(valueList -> textView.setText(userDataController.getUserName().substring(0,userDataController.getUserName().indexOf(" "))));
        userDataController.getAlreadyVisitedAttractionsFromDB(valueList -> setSatistics());


        badgeList = generateBadgeItems();

        //badgeRecyclerView = rootView.findViewById(R.id.badges_recyclerview); //From tutorial, but using binding is easier :))
        badgeRecyclerView = binding.badgesRecyclerview;
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        badgeRecyclerView.setLayoutManager(layoutManager);
        badgeAdapter = new BadgeAdapter(badgeList);
        badgeRecyclerView.setAdapter(badgeAdapter);

        /////////////////////////////////////////////////////////////


        communityActivityItemList = generateCommunityActivityItems();
        communityRecyclerView = binding.communityRecyclerview;
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        communityRecyclerView.setLayoutManager(layoutManager1);
        communityAdapter = new CommunityAdapter(communityActivityItemList);
        communityRecyclerView.setAdapter(communityAdapter);

        statAllTime = binding.totalDiscCountTV;
        statAllTime.setText("");
        statYear = binding.thisYearCount;
        statYear.setText("");
        statMonth = binding.thisMonthCount;
        statMonth.setText("");
        statWeek = binding.thisWeekCount;
        statWeek.setText("");



        return root;
    }

    private List<BadgeItem> generateBadgeItems(){
        //Should be fetched from db
        List<BadgeItem> dummyBadgeItems = new ArrayList<>();
        dummyBadgeItems.add(new BadgeItem(R.drawable.novice_explorer_badge,"Novice Explorer Badge","Make 50 total discoveries to achieve this bagde",43,50));
        dummyBadgeItems.add(new BadgeItem(R.drawable.church_badge,"The Pilgrim Badge","Visit all the 10 danish cathedrals",2,10));
        dummyBadgeItems.add(new BadgeItem(R.drawable.troll_badge,"The Trolls of Denmark Badge","Visit all the trolls in Denmark made by the artist Thomas Dambo",7,26));
        return dummyBadgeItems;
    }

    private List<CommunityActivityItem> generateCommunityActivityItems(){
        //Should be fetched from db
        List<CommunityActivityItem> dummyCommunityItems = new ArrayList<>();
        dummyCommunityItems.add(new CommunityActivityItem(true,R.drawable.woman_profile_pic,R.drawable.church_badge,"Pilgrim Badge", "Susan J."));
        dummyCommunityItems.add(new CommunityActivityItem(false,R.drawable.hvide_maend_smol,"De Hvide Mænd","Jeppe K."));
        dummyCommunityItems.add(new CommunityActivityItem(false,R.drawable.frederiksborg_smol,"Frederiksborg Slot","Charlotte H."));
        dummyCommunityItems.add(new CommunityActivityItem(true,R.drawable.man_profile_pic1,R.drawable.intermediate_badge,"Intermediate Explorer", "Jeppe K."));
        dummyCommunityItems.add(new CommunityActivityItem(false,R.drawable.kanal_rundfart_smol,"Nyhavn","Tina L."));
        dummyCommunityItems.add(new CommunityActivityItem(true,R.drawable.man_profile_pic2,R.drawable.troll_badge,"The Trolls of Denmark", "Timmy P."));

        return dummyCommunityItems;
    }

    public void setSatistics(){
        ArrayList<String> visitedAttractions= null;
        visitedAttractions = userDataController.getVisitedAttractions();
        ArrayList<String> visitedAttractionsDates = null;
        visitedAttractionsDates = userDataController.getVisitedAttractionsDates();

        this.numberOfAdventuresAllTime = visitedAttractions.size();
        this.numberOfAttractionsThisYear = 0;
        this.numberOfAttractionsThisMonth = 0;
        this.numberOfAttractionsThisWeek = 0;

        for(String visit : visitedAttractionsDates){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

            // Assume currentDate is the current date and oldDate is the date to compare
            Date currentDate = new Date();
            Date dateToCheck = null;
            try {
                dateToCheck = dateFormat.parse(visit);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Calendar calendar = Calendar.getInstance();

            // Check if the date is in the current year
            calendar.setTime(currentDate);
            int currentYear = calendar.get(Calendar.YEAR);

            calendar.setTime(dateToCheck);
            int yearToCheck = calendar.get(Calendar.YEAR);

            if (currentYear == yearToCheck) {
                Log.e("DATESS", "The date is in the current year: " + visit + "\nCurrent year is: "+ currentYear + "\n Year to Check: "+yearToCheck);
                numberOfAttractionsThisYear++;
                // Check if the date is in the current month
                calendar.setTime(currentDate);
                int currentMonth = calendar.get(Calendar.MONTH);

                calendar.setTime(dateToCheck);
                int monthToCheck = calendar.get(Calendar.MONTH);

                if (currentMonth == monthToCheck) {
                    Log.e("DATESS", "The date is in the current month: " + visit + "\nCurrent month is: "+ currentMonth + "\n Month to Check: "+monthToCheck);
                    numberOfAttractionsThisMonth++;
                }else{
                    Log.e("DATESS", "The date is NOT in the current month: " + visit + "\nCurrent month is: "+ currentMonth + "\n Month to Check: "+monthToCheck);
                }

                // Check if the date is in the current week
                calendar.setTime(currentDate);
                int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

                calendar.setTime(dateToCheck);
                int weekToCheck = calendar.get(Calendar.WEEK_OF_YEAR);

                if (currentWeek == weekToCheck) {
                    Log.e("DATESS", "The date is in the current week: " + visit + "\nCurrent week is: "+ currentWeek + "\n Week to Check: "+weekToCheck);
                    numberOfAttractionsThisWeek++;
                }else{
                    Log.e("DATESS", "The date is NOT in the current week: " + visit + "\nCurrent week is: "+ currentWeek + "\n Week to Check: "+weekToCheck);
                }

            }else{
                Log.e("DATESS", "The date is NOT in the current year: " + visit + "\nCurrent year is: "+ currentYear + "\n Year to Check: "+yearToCheck);
            }









        }

        statAllTime.setText(numberOfAdventuresAllTime+"");
        statYear.setText(numberOfAttractionsThisYear+"");
        statMonth.setText(numberOfAttractionsThisMonth+"");
        statWeek.setText(numberOfAttractionsThisWeek+"");
        }





}

