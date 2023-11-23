package com.example.allourtrees;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.AttractionsViewHolder> {

    private List<Attraction> attractionList;

    public AttractionsAdapter(List<Attraction> attractionList) {
        this.attractionList = attractionList;
    }

    @NonNull
    @Override
    public AttractionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_card, parent,false);
        return new AttractionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionsViewHolder holder, int position) {
        Attraction attraction = attractionList.get(position);
        holder.attractionTitle.setText(attraction.getAttractionName());
        holder.attractionDescriptionShort.setText(attraction.getAttractionDescriptionShort());
        if(attraction.getDistanceToAttraction()>5){
            int noDecimalDouble = (int) attraction.getDistanceToAttraction();
            holder.distanceToAttraction.setText(noDecimalDouble+" km");
        }else{
            holder.distanceToAttraction.setText(attraction.getDistanceToAttraction()+" km");
        }
        if(attraction.hasBeenVisitedBefore()){
            holder.beenThereStamp.setVisibility(View.VISIBLE);
        }else{
            holder.beenThereStamp.setVisibility(View.INVISIBLE);
        }

            switch(attraction.getPrice()){
                case 0:
                    holder.priceGroup.setVisibility(View.INVISIBLE);
                    //holder.attractionTitle.setText(attraction.getAttractionName()+ "");
                    Log.w("QLIST", "CASE 0");
                    break;
                case 1:
                    holder.priceGroup.setImageResource(R.drawable.price_1);
                    //holder.attractionTitle.setText(attraction.getAttractionName()+ "  $");
                    Log.w("QLIST", "CASE 1");
                    break;
                case 2:
                    holder.priceGroup.setImageResource(R.drawable.price_2);
                    //holder.attractionTitle.setText(attraction.getAttractionName()+ "  $$");
                    Log.w("QLIST", "CASE 2");
                    break;
                case 3:
                    holder.priceGroup.setImageResource(R.drawable.price_3);
                    //holder.attractionTitle.setText(attraction.getAttractionName()+ "  $$$");
                    Log.w("QLIST", "CASE 3");
                    break;
                case 4:
                    holder.priceGroup.setImageResource(R.drawable.price_4);
                    //holder.attractionTitle.setText(attraction.getAttractionName()+ "  $$$$");
                    Log.w("QLIST", "CASE 4");
                    break;


            }
        Log.e("QLIST", "Price is: " + attraction.getAttractionName() + ": " + attraction.getPrice());

    }


    @Override
    public int getItemCount() {
        return attractionList.size();
    }

    public static class AttractionsViewHolder extends RecyclerView.ViewHolder{
        TextView attractionTitle;
        TextView attractionDescriptionShort;
        ImageView priceGroup;
        ImageView beenThereStamp;
        TextView distanceToAttraction;

        public AttractionsViewHolder(@NonNull View itemView) {
            super(itemView);

            attractionTitle = itemView.findViewById(R.id.attraction_title_tv);
            attractionDescriptionShort = itemView.findViewById(R.id.attractionShortDescriptionTV);
            priceGroup = itemView.findViewById(R.id.priceGroupIV);
            beenThereStamp = itemView.findViewById(R.id.beenThereStampIV);
            distanceToAttraction = itemView.findViewById(R.id.distanceTV);
        }
    }
}
