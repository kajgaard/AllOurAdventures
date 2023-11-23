package com.example.allourtrees;

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
}
