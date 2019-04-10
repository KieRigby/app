package com.kierigby.bountyhunter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bountyhunterapi.BountyHunterStat;
import com.example.bountyhunterapi.Stat;

import java.util.List;

public class StatsRecyclerViewAdapter extends RecyclerView.Adapter<StatsRecyclerViewAdapter.MyViewHolder> {

    private static List<Stat> mStatList;
    private View.OnClickListener mOnItemClickListener;


    public StatsRecyclerViewAdapter(List<Stat> stats) {
        mStatList = stats;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView positionTextView, wOrlTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            positionTextView = itemView.findViewById(R.id.positionTextView);
            wOrlTextView = itemView.findViewById(R.id.wonOrLostTextView);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);

        }
    }

    @NonNull
    @Override
    public StatsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stat_item, viewGroup, false);
        return new StatsRecyclerViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        Stat statObj = mStatList.get(i);
        String position;
        String wonOrLost;

        if (statObj instanceof BountyHunterStat) {
            position = "Bounty Hunter";
        } else {
            position = "Fugitive";
        }

        if (statObj.isWon()) {
            wonOrLost = "Won";
        } else {
            wonOrLost = "Lost";
        }
        myViewHolder.positionTextView.append(" " + position);
        myViewHolder.wOrlTextView.append(" " + wonOrLost);
    }

    @Override
    public int getItemCount() {
        return mStatList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

}
