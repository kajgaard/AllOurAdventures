package com.example.allourtrees.ui.discover;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.allourtrees.Attraction;
import com.example.allourtrees.MainActivity;
import com.example.allourtrees.R;
import com.example.allourtrees.UserDataController;
import com.example.allourtrees.databinding.FragmentDiscoverBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment implements OnMapReadyCallback {

    private FragmentDiscoverBinding binding;

    GoogleMap gMap;
    Fragment map;
    ConstraintLayout layout;

    UserDataController userDataController = UserDataController.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiscoverViewModel discoverViewModel =
                new ViewModelProvider(this).get(DiscoverViewModel.class);

        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        layout = binding.layout;
        layout.setVisibility(View.GONE);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        insertMarkersAsAttraction();
        centerMapOnMyLocation();
        makeMapVisible();

    }

    private void makeMapVisible(){
        layout.setVisibility(View.VISIBLE);
    }

    private void insertMarkersAsAttraction() {
        ArrayList<Attraction> alreadyVisited = userDataController.getVisitedAttractionsAsObjects();
        List<Attraction> notVisited = userDataController.getAllNotVisitedAttractionsAsObjects();
        for (Attraction attraction : alreadyVisited) {
            LatLng pinMarker = new LatLng(attraction.getLattitude(), attraction.getLongitude());
            this.gMap.addMarker(new MarkerOptions().position(pinMarker).title(attraction.getAttractionName()).icon(BitmapFromVector(getContext(), R.drawable.seen_pin)));
        }
        for (Attraction attraction : notVisited) {
            LatLng pinMarker = new LatLng(attraction.getLattitude(), attraction.getLongitude());
            this.gMap.addMarker(new MarkerOptions().position(pinMarker).title(attraction.getAttractionName()).icon(BitmapFromVector(getContext(), R.drawable.unseen_pin)));
        }
    }

    private void centerMapOnMyLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);

        Location mLocation = MainActivity.getCurrentLocation();
        LatLng myLocation = new LatLng(mLocation.getLatitude(),
                mLocation.getLongitude());
        gMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

        // Zoom in, animating the camera.
        gMap.animateCamera(CameraUpdateFactory.zoomTo(12), 500, null);
        gMap.getUiSettings().setZoomControlsEnabled(false);
        gMap.getUiSettings().setCompassEnabled(false);
        gMap.getUiSettings().setMyLocationButtonEnabled(true);

        }



    private BitmapDescriptor
    BitmapFromVector(Context context, int vectorResId)
    {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(
                context, vectorResId);

        // below line is use to set bounds to our vector
        // drawable.
        vectorDrawable.setBounds(
                0, 0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(
                vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our
        // bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



}