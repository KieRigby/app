package com.kierigby.bountyhunter;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.BountyHunterStat;
import com.example.bountyhunterapi.FugitiveStat;
import com.example.bountyhunterapi.Stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private final List<Stat> statsList = Collections.synchronizedList(new ArrayList<Stat>());
    private BountyHunterAPI api;
    private StatsRecyclerViewAdapter mStatRecyclerViewAdapter;
    private View.OnClickListener mItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GlobalUser) this.getApplication()).tokenCheck();
        setContentView(R.layout.activity_stats);
        api = new BountyHunterAPI(this);
        mItemClickListener = createClickListener();
        addListenerToBackButton();
        setUpRecyclerView();
        getStats();

    }


    private void setUpRecyclerView() {
        RecyclerView mStatsRecyclerView = findViewById(R.id.stats_recycler_view);
        mStatsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStatRecyclerViewAdapter = new StatsRecyclerViewAdapter(statsList);
        mStatsRecyclerView.setAdapter(mStatRecyclerViewAdapter);
        mStatRecyclerViewAdapter.setOnItemClickListener(mItemClickListener);
    }

    private void getStats() {
        api.getBountyHunterStats(((GlobalUser) this.getApplication()).getLoggedInUser().getId(), new BountyHunterAPI.StatCallBack() {
            @Override
            public void onStatsRetrieved(List<Stat> stats) {
                synchronized (statsList) {
                    statsList.addAll(stats);
                    mStatRecyclerViewAdapter.notifyItemInserted(mStatRecyclerViewAdapter.getItemCount());
                }
            }
        });

        api.getFugitiveStats(((GlobalUser) this.getApplication()).getLoggedInUser().getId(), new BountyHunterAPI.StatCallBack() {
            @Override
            public void onStatsRetrieved(List<Stat> stats) {
                synchronized (statsList) {
                    statsList.addAll(stats);
                    mStatRecyclerViewAdapter.notifyItemInserted(mStatRecyclerViewAdapter.getItemCount());
                }
            }
        });
    }

    public void addListenerToBackButton() {
        ImageButton mBackButton = findViewById(R.id.backFromStats);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(StatsActivity.this);
            }
        });
    }

    private View.OnClickListener createClickListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                Stat statToDisplay = statsList.get(viewHolder.getAdapterPosition());
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(StatsActivity.this);
                View mStatInfoDisplay = getLayoutInflater().inflate(R.layout.stats_pop_up, null);

                TextView mPositionTextView = mStatInfoDisplay.findViewById(R.id.positionPopUp);
                TextView mPointsTextView = mStatInfoDisplay.findViewById(R.id.pointsPopUp);
                TextView mwOrlTextView = mStatInfoDisplay.findViewById(R.id.wonOrLostPopUp);
                TextView mCapturesTextView = mStatInfoDisplay.findViewById(R.id.capturesPopUp);
                TextView mChallengesCompletedTextView = mStatInfoDisplay.findViewById(R.id.challengesCompletedPopUp);
                TextView mChallengesFailedTextView = mStatInfoDisplay.findViewById(R.id.challengesFailedPopUp);

                mPointsTextView.append(" " + statToDisplay.getPoints());

                if (statToDisplay.isWon()) {
                    mwOrlTextView.append(" Won");
                } else {
                    mwOrlTextView.append(" Lost");
                }

                if (statToDisplay instanceof BountyHunterStat) {
                    mPositionTextView.append(" Bounty Hunter");
                    mCapturesTextView.append(" " + ((BountyHunterStat) statToDisplay).getCaptures());
                    mChallengesCompletedTextView.setVisibility(View.GONE);
                    mChallengesFailedTextView.setVisibility(View.GONE);
                } else {
                    mPositionTextView.append(" Fugitive");
                    mChallengesCompletedTextView.append(" "+ ((FugitiveStat)statToDisplay).getChallengesCompleted());
                    mChallengesFailedTextView.append(" "+((FugitiveStat)statToDisplay).getChallengesFailed());
                    mCapturesTextView.setVisibility(View.GONE);
                }

                //Sets the view of the dialogue window use a custom layout
                mBuilder.setView(mStatInfoDisplay);
                //Creates and shows the dialogue window
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        };
    }
}
