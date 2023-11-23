package com.example.allourtrees;

import android.util.Log;

public class BadgeItem {
    private int badgeIcon;
    private String badgeName;
    private String badgeDescription;
    private int currentProgress;
    private int progressForFull;

    public int getBadgeIcon() {
        return badgeIcon;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public int getProgressForFull() {
        return progressForFull;
    }

    public BadgeItem(int badgeIcon, String badgeName, String badgeDescription, int currentProgress, int progressForFull) {
        this.badgeIcon = badgeIcon;
        this.badgeName = badgeName;
        this.badgeDescription = badgeDescription;
        this.currentProgress = currentProgress;
        this.progressForFull = progressForFull;
    }

    public void addProgress(){
        this.currentProgress++;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getPercentFinished(){
        int x = (this.currentProgress*100/this.progressForFull)*100;

        Log.e("BADGELIST", "Percent finished for " + this.badgeName + "% = "+ x+"\nProgressofrFull: "+this.progressForFull+"\nCurrentProgress: "+ this.currentProgress);
        return x;
    }

    @Override
    public String toString() {
        return "BadgeItem{" +
                "badgeIcon=" + badgeIcon +
                ", badgeName='" + badgeName + '\'' +
                ", badgeDescription='" + badgeDescription + '\'' +
                ", currentProgress=" + currentProgress +
                ", progressForFull=" + progressForFull +
                '}';
    }

    /*
    @Override
    public int compareTo(BadgeItem compareBadge) {
        int comparePercentFinished = (((BadgeItem) compareBadge).getPercentFinished());
        /* For Ascending order*/
        //return this.getPercentFinished()-comparePercentFinished;

        /* For Descending order do like this */
        //return comparePercentFinished-this.getPercentFinished();
    //}



}
