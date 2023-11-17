package com.example.allourtrees;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<CommunityActivityItem> communityActivityList;

    public CommunityAdapter(List<CommunityActivityItem> communityActivityList) {
        this.communityActivityList = communityActivityList;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType) {
            case 0:
                return new CommunityBadgeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.community_badge_card, parent, false));

            case 2:
                return new CommunityDiscoveryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.community_discovery_card, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        CommunityActivityItem communityActivityItem = communityActivityList.get(position);

        switch (holder.getItemViewType()){
            case 0:
                CommunityBadgeViewHolder mholder = (CommunityBadgeViewHolder) holder;
                mholder.friendName.setText("Your friend " + communityActivityItem.getFriendName()+ " has earned the badge");
                mholder.badgeName.setText(communityActivityItem.getBadgeName());
                mholder.friendProfilePic.setImageResource((communityActivityItem.getFriendProfilePic()));
                mholder.friendBadgePic.setImageResource(communityActivityItem.getFriendBadgePic());
                break;
            case 2:
                CommunityDiscoveryViewHolder mholder1 = (CommunityDiscoveryViewHolder) holder;
                mholder1.friendName.setText("Your friend "+ communityActivityItem.getFriendName()+ " has discovered");
                mholder1.adventureName.setText(communityActivityItem.getAdventureName());
                mholder1.friendPictureTaken.setImageResource(communityActivityItem.getFriendPictureTaken());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return communityActivityList.size();
    }

    public static class CommunityBadgeViewHolder extends RecyclerView.ViewHolder{
        //For a Friend Earned Badge event
        ImageView friendProfilePic;
        ImageView friendBadgePic;
        TextView badgeName;
        CardView friendProfilePicHolder;

        TextView friendName;
        boolean isABadgeEvent;


        public CommunityBadgeViewHolder(@NonNull View itemView) {
            super(itemView);
            friendProfilePic = itemView.findViewById(R.id.friend_image);
            friendBadgePic = itemView.findViewById(R.id.badge_iv);
            badgeName = itemView.findViewById(R.id.activity_main_title_tv);
            friendProfilePicHolder = itemView.findViewById(R.id.friend_picture_cv);
            friendName = itemView.findViewById(R.id.activity_title_tv);
        }
    }

    public static class CommunityDiscoveryViewHolder extends RecyclerView.ViewHolder{
        //For a Friend Discovered Adventure event
        ImageView friendPictureTaken;
        TextView adventureName;
        CardView friendPicTakenHolder;
        TextView friendName;
        boolean isABadgeEvent;


        public CommunityDiscoveryViewHolder(@NonNull View itemView) {
            super(itemView);
            friendPictureTaken = itemView.findViewById(R.id.activity_image);
            friendPicTakenHolder = itemView.findViewById(R.id.big_picture_cv);
            adventureName = itemView.findViewById(R.id.activity_main_title_tv);
            friendName = itemView.findViewById(R.id.activity_title_tv);
        }
    }
}
