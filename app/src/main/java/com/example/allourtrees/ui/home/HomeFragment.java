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

import com.example.allourtrees.Attraction;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

    private ArrayList<BadgeItem> badgeList;
    private RecyclerView badgeRecyclerView;
    private BadgeAdapter badgeAdapter;


    private List<CommunityActivityItem> communityActivityItemList;
    private RecyclerView communityRecyclerView;
    private CommunityAdapter communityAdapter;
    UserDataController userDataController = UserDataController.getInstance();

    TextView statAllTime, statWeek, statMonth, statYear, todayDate;

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

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Define the formatter with the desired pattern and locale
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d'TH' MMMM yyyy", Locale.ENGLISH);

        // Format the current date using the formatter
        String formattedDate = currentDate.format(formatter);

        // Convert the formatted date to uppercase
        formattedDate = formattedDate.toUpperCase();

        todayDate = binding.currentDateTV;
        todayDate.setText(formattedDate);


        //////////////////////////////////////////////////////////
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false); //From tutorial, but using binding is easier :))

        //textView = rootView.findViewById(R.id.name_of_user_TV); //From tutorial, but using binding is easier :))
        textView = binding.nameOfUserTV;
        textView.setText("");
        userDataController.getUserNameFromDB(valueList -> textView.setText(userDataController.getUserName().substring(0,userDataController.getUserName().indexOf(" "))));
        userDataController.getAlreadyVisitedAttractionsFromDB(valueList -> {

            badgeList = generateBadgeItems();


            setSatistics();
            updateBadgeProgress();

            Log.e("BADGELIST", "Before sort: " + badgeList.toString());
            sortListByAttribute(badgeList);
            Log.e("BADGELIST", "After sort: " + badgeList.toString());


            //badgeRecyclerView = rootView.findViewById(R.id.badges_recyclerview); //From tutorial, but using binding is easier :))
            badgeRecyclerView = binding.badgesRecyclerview;
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            badgeRecyclerView.setLayoutManager(layoutManager);
            badgeAdapter = new BadgeAdapter(badgeList);
            badgeRecyclerView.setAdapter(badgeAdapter);


        });




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

    // Method to sort the list based on the 'myAttribute' attribute
    private void sortListByAttribute(ArrayList<BadgeItem> list) {
        list.sort(new Comparator<BadgeItem>() {
            @Override
            public int compare(BadgeItem obj1, BadgeItem obj2) {
                // Compare based on the 'myAttribute' attribute
                return Integer.compare(obj2.getPercentFinished(), obj1.getPercentFinished());
            }
        });
    }
    private ArrayList<BadgeItem> generateBadgeItems(){
        //Should be fetched from db

        ArrayList<BadgeItem> dummyBadgeItems = new ArrayList<>();
        //dummyBadgeItems.add(new BadgeItem(R.drawable.novice_explorer_badge,"Novice Explorer Badge","Make 50 total discoveries to achieve this bagde",43,50));
        //dummyBadgeItems.add(new BadgeItem(R.drawable.church_badge,"The Pilgrim Badge","Visit all the 10 danish cathedrals",2,10));
        //dummyBadgeItems.add(new BadgeItem(R.drawable.troll_badge,"The Trolls of Denmark Badge","Visit all the trolls in Denmark made by the artist Thomas Dambo",7,26));

        dummyBadgeItems.add(new BadgeItem(R.drawable.novice_explorer_badge, "Discovery Quest","Embark on an epic expedition by visiting all the attractions", 0, 35));
        dummyBadgeItems.add(new BadgeItem(R.drawable.nature_badge, "Eco Explorer","Navigate the nurturing nooks of nature by visiting all of the wonderful nature sites", 0, 3));
        dummyBadgeItems.add(new BadgeItem(R.drawable.park_badge, "Green Oasis Guru","Pounce on a picturesque park pilgrimage and visit all of the parks", 0, 4));
        dummyBadgeItems.add(new BadgeItem(R.drawable.teater_badge, "Stage Maestro","Thrust yourself into the thrilling realm of theaters and visit all of the theaters", 0, 5));
        dummyBadgeItems.add(new BadgeItem(R.drawable.aquarium_badge, "Aqua Ambassador","Appreciate an awe-inspiring array of Aquariums, by visiting all the aquariums", 0, 3));
        dummyBadgeItems.add(new BadgeItem(R.drawable.museum_badge, "Museum Master","Discover a magnificent maze of museums by visiting all the museums", 0, 14));
        dummyBadgeItems.add(new BadgeItem(R.drawable.history_badge, "Time Traveller","Heightening your historical horizon by visiting all the historical sites", 0, 5));
        dummyBadgeItems.add(new BadgeItem(R.drawable.centre_badge, "Center Chase","Celebrate the captivating corridors by visiting all the centers", 0, 3));
        dummyBadgeItems.add(new BadgeItem(R.drawable.resort_badge, "Paradise Pursuit","Realize a rejuvenating retreat by visiting all the resorts", 0, 1));
        dummyBadgeItems.add(new BadgeItem(R.drawable.castle_badge, "Fortress Connoisseur","Conquer the colossal charm of past times by visiting all the castles", 0, 5));
        dummyBadgeItems.add(new BadgeItem(R.drawable.zoo_badge, "Wild Warden","Zip into the wild world by visiting all the zoos", 0, 7));
        dummyBadgeItems.add(new BadgeItem(R.drawable.amusement_badge, "Joyride Juggernaut","Pursue an adrenaline-filled adventure by visiting all the amusement parks", 0, 7));


        return dummyBadgeItems;
    }

    void updateBadgeProgress(){
        ArrayList<String> visitedAttractions1= null;
        visitedAttractions1 = userDataController.getVisitedAttractions();



        List<Attraction> allAttractions= null;
        allAttractions = ((MainActivity)getActivity()).getAttractionList();

        Log.e("BADGESS", "AttrationsList: "+allAttractions.size());
        Log.e("BADGESS", "VisitedList: "+visitedAttractions1);
        for(String visit : visitedAttractions1){


            for(Attraction attraction : allAttractions){

                if(attraction.getAttractionName().equals(visit)){
                    Log.w("BADGESS","Found match in list for:"+ visit);
                    for(BadgeItem badge : badgeList){
                        if(badge.getBadgeName().equals("Discovery Quest")){
                            badge.addProgress();
                        }
                    }

                    for(String category : attraction.getAttractionCategory()){
                        Log.w("BADGESS","Categories for attraction: "+visit+" List: "+attraction.getAttractionCategory());

                        switch (category) {

                            case "Castle":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Fortress Connoisseur")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and CASTLE");

                                    }
                                }
                                break;
                            case "Nature":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Eco Explorer")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and NATURE");

                                    }
                                }
                                break;
                            case "Park":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Green Oasis Guru")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and PARK");

                                    }
                                }
                                break;
                            case "Theatre":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Stage Maestro")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and TEATER");

                                    }
                                }
                                break;
                            case "Aquarium":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Aqua Ambassador")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and AQUARIUM");

                                    }
                                }
                                break;
                            case "Museum":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Museum Master")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and MUSEUM");
                                    }
                                }
                                break;
                            case "History":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Time Traveller")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and HISTORY");

                                    }
                                }
                                break;
                            case "Center":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Center Chase")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and CENTER");

                                    }
                                }
                                break;
                            case "Resort":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Paradise Pursuit")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and RESORT");

                                    }
                                }
                                break;
                            case "Zoo":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Wild Warden")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and ZOO");

                                    }
                                }
                                break;
                            case "Amusement Park":
                                for(BadgeItem badge : badgeList){
                                    if(badge.getBadgeName().equals("Joyride Juggernaut")){
                                        badge.addProgress();
                                        Log.w("BADGESS","Category matched for: "+visit+" Badge: "+badge.getBadgeName()+ " and AMUSEMENT");

                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    private List<CommunityActivityItem> generateCommunityActivityItems(){
        //Should be fetched from db
        List<CommunityActivityItem> dummyCommunityItems = new ArrayList<>();
        dummyCommunityItems.add(new CommunityActivityItem(true,R.drawable.woman_profile_pic,R.drawable.castle_badge,"Time Traveller", "Susan J."));
        dummyCommunityItems.add(new CommunityActivityItem(false,R.drawable.hvide_maend_smol,"De Hvide Mænd","Tom K."));
        dummyCommunityItems.add(new CommunityActivityItem(false,R.drawable.frederiksborg_smol,"Frederiksborg Slot","Charlotte H."));
        dummyCommunityItems.add(new CommunityActivityItem(true,R.drawable.man_profile_pic1,R.drawable.park_badge,"Green Oasis Guru", "Tom K."));
        dummyCommunityItems.add(new CommunityActivityItem(false,R.drawable.kanal_rundfart_smol,"Nyhavn","Tina L."));
        dummyCommunityItems.add(new CommunityActivityItem(true,R.drawable.man_profile_pic2,R.drawable.zoo_badge,"Wild Warden", "Timmy P."));

        return dummyCommunityItems;
    }

    public void setSatistics(){



        ArrayList<String> visitedAttractions= null;
        visitedAttractions = userDataController.getVisitedAttractions();
        ArrayList<String> visitedAttractionsDates = null;
        visitedAttractionsDates = userDataController.getVisitedAttractionsDates();
        Log.e("BADGESS", "VisitedList FROM STATS: "+visitedAttractions);
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

