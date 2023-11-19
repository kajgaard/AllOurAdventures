package com.example.allourtrees;

public class Attraction {
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

    public boolean hasBeenVisitedBefore(){
        return true;
    }
}
