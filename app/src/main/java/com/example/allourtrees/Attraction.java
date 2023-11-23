package com.example.allourtrees;


import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Attraction {
    private static final double EARTH_RADIUS = 6371;
    double longitude;
    double lattitude;

    String attractionName;
    String attractionDescriptionShort;
    String attractionDescriptionLong;

    int attractionDefaultPicture;
    ArrayList<String> attractionCategory;

    int price; //ranged from 0-4??


    public Attraction(double lattitude, double longitude, String attractionName, String attractionDescriptionShort, String attractionDescriptionLong, int attractionDefaultPicture, ArrayList<String> attractionCategory, int price) {
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.attractionName = attractionName;
        this.attractionDescriptionShort = attractionDescriptionShort;
        this.attractionDescriptionLong = attractionDescriptionLong;
        this.attractionDefaultPicture = attractionDefaultPicture;
        this.attractionCategory = attractionCategory;
        this.price = price;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public String getAttractionDescriptionShort() {
        return attractionDescriptionShort;
    }

    public String getAttractionDescriptionLong() {
        return attractionDescriptionLong;
    }

    public int getAttractionDefaultPicture() {
        return attractionDefaultPicture;
    }

    public ArrayList<String> getAttractionCategory() {
        return attractionCategory;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "longitude=" + longitude +
                ", lattitude=" + lattitude +
                ", attractionName='" + attractionName + '\'' +
                ", attractionDescriptionShort='" + attractionDescriptionShort + '\'' +
                ", attractionDescriptionLong='" + attractionDescriptionLong + '\'' +
                ", attractionDefaultPicture=" + attractionDefaultPicture +
                ", attractionCategory='" + attractionCategory + '\'' +
                ", price=" + price +
                '}';
    }

    public double getDistanceToAttraction(){
        Location currentLocation = getCurrentLocation();
        if(currentLocation != null) {
            double distanceInMeters = calculateDistance(currentLocation.getLatitude(), getLattitude(), currentLocation.getLongitude(), getLongitude(), 0, 0);
            double distanceInKm = distanceInMeters / 1000;
            if(distanceInKm > 5){
                distanceInKm = round(distanceInKm, 0);
                distanceInKm = Double.parseDouble(new DecimalFormat("#").format(distanceInKm));
            }else{
                distanceInKm = round(distanceInKm, 1);
            }
            Log.w("MARIA", "Using values:\nCurrent pos Lat: " + currentLocation.getLatitude() + "\nCurrent pos Lon: " + currentLocation.getLongitude() + "\nAttraction lat: " + getLattitude() + "\n Attraction Lon: " + getLongitude() + "\n DISTANCE IS: " + distanceInKm);
            return (distanceInKm);
        }else{
            return(0.0);
        }
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double calculateDistance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
    boolean hasBeenVisitedBefore(){

        ArrayList<String> list = MainActivity.visitedAttractions;
        for (String attraction : list){
            if (attraction.equals(this.attractionName)){
                Log.e("BEENTHERE", "Looking for "+ this.attractionName +"in visited attractions and found it");
                int x = 10;
                return true;
            }
        }

        return false;
    }

    public Location getCurrentLocation(){
        return(MainActivity.getCurrentLocation());
    }

}
