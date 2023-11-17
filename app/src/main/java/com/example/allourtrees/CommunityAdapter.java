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

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    List<CommunityActivityItem> communityActivityList;

    public CommunityAdapter(List<CommunityActivityItem> communityActivityList) {
        this.communityActivityList = communityActivityList;
    }

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_card,parent,false);
        return new CommunityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {
        CommunityActivityItem communityActivityItem = communityActivityList.get(position);
        if(communityActivityItem.isABadgeEvent() == true){
            holder.friendName.setText("Your friend " + communityActivityItem.getFriendName()+ " has earned the badge");
            holder.badgeName.setText(communityActivityItem.getBadgeName());
            holder.friendProfilePic.setImageResource((communityActivityItem.getFriendProfilePic()));
            holder.friendProfilePicHolder.setVisibility(View.VISIBLE);
            holder.friendBadgePic.setImageResource(communityActivityItem.getFriendBadgePic());
            holder.friendBadgePic.setVisibility(View.VISIBLE);
        }else{
            holder.friendName.setText("Your friend "+ communityActivityItem.getFriendName()+ " has discovered");
            holder.adventureName.setText(communityActivityItem.getAdventureName());
            holder.friendPictureTaken.setImageResource(communityActivityItem.getFriendPictureTaken());
            holder.friendPicTakenHolder.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return communityActivityList.size();
    }

    public static class CommunityViewHolder extends RecyclerView.ViewHolder{
        //For a Friend Earned Badge event
        ImageView friendProfilePic;
        ImageView friendBadgePic;
        TextView badgeName;
        CardView friendProfilePicHolder;


        //For a Friend Discovered Adventure event
        ImageView friendPictureTaken;
        TextView adventureName;
        CardView friendPicTakenHolder;

        //Shared
        TextView friendName;
        boolean isABadgeEvent;


        public CommunityViewHolder(@NonNull View itemView) {
            super(itemView);

            if(isABadgeEvent) {
                friendProfilePic = itemView.findViewById(R.id.friend_image);
                friendBadgePic = itemView.findViewById(R.id.badge_iv);
                badgeName = itemView.findViewById(R.id.activity_main_title_tv);
                friendProfilePicHolder = itemView.findViewById(R.id.friend_picture_cv);

            }else{
                friendPictureTaken = itemView.findViewById(R.id.activity_image);
                friendPicTakenHolder = itemView.findViewById(R.id.big_picture_cv);
                adventureName = itemView.findViewById(R.id.activity_main_title_tv);
            }
            friendName = itemView.findViewById(R.id.activity_title_tv);
        }
    }
}
