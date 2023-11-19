package com.example.allourtrees;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Attraction {
    private static final double EARTH_RADIUS = 6371;
    double longitude;
    double lattitude;

    String attractionName;
    String attractionDescriptionShort;
    String attractionDescriptionLong;

    int attractionDefaultPicture;
    String attractionCategory;

    int price; //ranged from 0-4??




    public Attraction(double longitude, double lattitude, String attractionName, String attractionDescriptionShort, String attractionDescriptionLong, int attractionDefaultPicture, String attractionCategory) {
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.attractionName = attractionName;
        this.attractionDescriptionShort = attractionDescriptionShort;
        this.attractionDescriptionLong = attractionDescriptionLong;
        this.attractionCategory = attractionCategory;
        this.attractionDefaultPicture = attractionDefaultPicture;
    }

    public Attraction(double longitude, double lattitude, String attractionName, String attractionDescriptionShort, String attractionDescriptionLong, int attractionDefaultPicture, String attractionCategory, int price) {
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

    public String getAttractionCategory() {
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

        return(0.0);
    }

    double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.sqrt(x * x + y * y) * EARTH_RADIUS;

        return distance;
    }

    public boolean hasBeenVisitedBefore(){
        return true;
    }

}
