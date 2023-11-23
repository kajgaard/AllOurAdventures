package com.example.allourtrees;

import java.util.Random;

public class Adventure {
    public int imageTaken;
    public String attractionName, notes, adventureId, visitDate, attractionId;

    public Adventure(){} //Empty constructor for firestore

    public Adventure(String attractionName, String visitDate){
        this.attractionName = attractionName;
        this.visitDate = visitDate;
        //String randomId = getRandomNumberString();
        //String uniqeId = randomId+this.visitDate.trim();
        //uniqeId = adventureId;
    }

    public Adventure(String attractionName, String visitDate, String notes){
        this.attractionName = attractionName;
        this.visitDate = visitDate;
        this.notes = notes;
        //String randomId = getRandomNumberString();
        //String uniqeId = randomId+this.visitDate.trim();
        //uniqeId = adventureId;
    }

    public Adventure(String attractionName, String visitDate, int imageTaken){
        this.attractionName = attractionName;
        this.visitDate = visitDate;
        this.imageTaken = imageTaken;
        //String randomId = getRandomNumberString();
        //String uniqeId = randomId+this.visitDate.trim();
        //uniqeId = adventureId;
    }

    public Adventure(String attractionName, String visitDate, String notes, int imageTaken){
        this.attractionName = attractionName;
        this.visitDate = visitDate;
        this.imageTaken = imageTaken;
        this.notes = notes;
        //String randomId = getRandomNumberString();
        //String uniqeId = randomId+this.visitDate.trim();
        //uniqeId = adventureId;
    }

    //Getters


    public String getAdventureId() {
        return adventureId;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getNotes() {
        return notes;
    }

    public int getImageTaken() {
        return imageTaken;
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
