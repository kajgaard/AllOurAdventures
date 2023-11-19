package com.example.allourtrees;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.allourtrees.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static String user;

    public UserDataController userDataController = new UserDataController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        getAttractionsFromCsv();

    }

    private List<Attraction> attractionList = new ArrayList<>();
    public void getAttractionsFromCsv(){
        InputStream is = getResources().openRawResource(R.raw.attractions);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {

                //Split by ';'
                String[] tokens = line.split(";");

                //Read the data
                Attraction attraction = new Attraction(Double.valueOf(tokens[0]), Double.valueOf(tokens[1]), tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), tokens[6], Integer.parseInt(tokens[7]));
                attractionList.add(attraction);

                Log.w("MARIA", "Just created: " + attraction);
            }
        }catch (IOException e){
            Log.wtf("FileInput", "Error handling data file on line: " + line, e);
        }

    }

}

