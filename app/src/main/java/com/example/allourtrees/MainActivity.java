package com.example.allourtrees;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.allourtrees.ui.new_entry.NewEntryFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.allourtrees.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavController.OnDestinationChangedListener {

    private ActivityMainBinding binding;

    public static String user;

    public UserDataController userDataController = new UserDataController();

    FloatingActionButton newEntryBtn;
    private final int FINE_PERMISSION_CODE = 1;
    public static Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public boolean fabFlag = false;

    BottomNavigationView navView;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    public NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        newEntryBtn = binding.fab;
        newEntryBtn.setOnClickListener(this);

        navController.addOnDestinationChangedListener(this);

        getAttractionsFromCsv();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    Log.w("MARIA", "Current location is: " + location);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this,"Location permission is denied, please allow this permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public List<Attraction> attractionList = new ArrayList<>();
    public void getAttractionsFromCsv(){
        InputStream is = getResources().openRawResource(R.raw.attractions_wiki);
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


    public List<Attraction> getAttractionList(){
        return attractionList;
    }

    public static Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void onClick(View view) {

        if(fabFlag){
            if(view == newEntryBtn){
                Log.e("BUTTON", "I am main, and should close"); //Is never used because fragment overrides clicklistener when the frag is open
                //navController.popBackStack();
                //navController.navigate(R.id.navigation_home);

            }
        }else{ //New entry should be opened
            if(view == newEntryBtn){
                navController.popBackStack();
                Log.e("BUTTON", "I am main, and should open");

                navController.navigate(R.id.navigation_new_entry);

                uncheckAllItems(this.navView);
            }
        }

    }

    public static void uncheckAllItems(BottomNavigationView bottomNavigationView) {
        Menu menu = bottomNavigationView.getMenu();
        menu.setGroupCheckable(0, true, false);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setChecked(false);
        }
        menu.setGroupCheckable(0, true, true);
    }

    @Override
    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
        if(navDestination.getId() == R.id.navigation_new_entry){
            newEntryBtn.setImageResource(R.drawable.done_icon);
            fabFlag = true;
            Log.w("BUTTON", "Destination is FAB and flag is: " + fabFlag);

        }else{
            newEntryBtn.setImageResource(R.drawable.add_icon);
            fabFlag = false;
            newEntryBtn.setOnClickListener(this);
            Log.w("BUTTON", "Destination is NOT FAB and flag is: " + fabFlag);

        }
    }
}

