package com.example.allourtrees;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {

    private List<BadgeItem> badgeList;

    public BadgeAdapter(List<BadgeItem> badgeList) {
        this.badgeList = badgeList;
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_card, parent, false);
        return new BadgeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        BadgeItem badgeItem = badgeList.get(position);
        holder.badgeTitle.setText(badgeItem.getBadgeName());
        holder.badgeDescription.setText(badgeItem.getBadgeDescription());
        holder.badgeIcon.setImageResource(badgeItem.getBadgeIcon());
        holder.currentProgress.setText(badgeItem.getCurrentProgress()+" / "+badgeItem.getProgressForFull());
        holder.progressBar.setMax(badgeItem.getProgressForFull());
        holder.progressBar.setProgress(badgeItem.getCurrentProgress());
    }

    @Override
    public int getItemCount() {
        return badgeList.size();
    }

    public static class BadgeViewHolder extends RecyclerView.ViewHolder{
        ImageView badgeIcon;
        TextView badgeTitle;
        TextView badgeDescription;
        TextView currentProgress;
        ProgressBar progressBar;

        public BadgeViewHolder(@NonNull View itemView) {
            super(itemView);
            badgeIcon = itemView.findViewById(R.id.badge_icon_IV);
            badgeTitle = itemView.findViewById(R.id.activity_title_tv);
            badgeDescription = itemView.findViewById(R.id.activity_main_title_tv);
            currentProgress = itemView.findViewById(R.id.badge_current_progress);
            progressBar = itemView.findViewById(R.id.badge_progressBar);
        }
    }
}
