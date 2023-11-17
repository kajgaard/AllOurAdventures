package com.example.allourtrees;

public class CommunityActivityItem {

    //For a Friend Earned Badge event
    private int friendProfilePic;
    private int friendBadgePic;

    private String badgeName;


    //For a Friend Discovered Adventure event
    private int friendPictureTaken;
    private String adventureName;

    //Shared
    private String friendName;

    private boolean isABadgeEvent;



    //Constructor for a "Friend Discovered Adventure event"
    public CommunityActivityItem(boolean isABadgeEvent, int friendPictureTaken, String adventureName, String friendName) {
        this.isABadgeEvent = isABadgeEvent;
        this.friendPictureTaken = friendPictureTaken;
        this.adventureName = adventureName;
        this.friendName = friendName;
    }


    //Constructor for a "Friend Earned Badge event"
    public CommunityActivityItem(boolean isABadgeEvent, int friendProfilePic, int friendBadgePic, String badgeName, String friendName) {
        this.isABadgeEvent = isABadgeEvent;
        this.friendProfilePic = friendProfilePic;
        this.friendBadgePic = friendBadgePic;
        this.badgeName = badgeName;
        this.friendName = friendName;
    }

    public boolean isABadgeEvent() {
        return isABadgeEvent;
    }

    public int getFriendProfilePic() {
        return friendProfilePic;
    }

    public int getFriendBadgePic() {
        return friendBadgePic;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public int getFriendPictureTaken() {
        return friendPictureTaken;
    }

    public String getAdventureName() {
        return adventureName;
    }

    public String getFriendName() {
        return friendName;
    }
}
