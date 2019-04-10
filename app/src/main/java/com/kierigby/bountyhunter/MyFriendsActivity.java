package com.kierigby.bountyhunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.Friend;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyFriendsActivity extends AppCompatActivity {

    private final List<Friend> friendsList = Collections.synchronizedList(new ArrayList<Friend>());
    private BountyHunterAPI api;
    private FriendsRecyclerViewAdapter mFriendRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GlobalUser) this.getApplication()).tokenCheck();
        setContentView(R.layout.activity_my_friends);
        api = new BountyHunterAPI(this);
        addListenerToBackButton();
        addListerToFindFriendsButton();
        setUpRecyclerView();
        getFriends();
    }


    private void setUpRecyclerView() {

        RecyclerView mFriendRecyclerView = findViewById(R.id.friends_recycler_view);
        mFriendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFriendRecyclerViewAdapter = new FriendsRecyclerViewAdapter(friendsList);
        mFriendRecyclerView.setAdapter(mFriendRecyclerViewAdapter);

    }

    public void getFriends() {

        api.getFriendsFollowing(((GlobalUser) this.getApplication()).getLoggedInUser().getId(), new BountyHunterAPI.FoundFriendsCallBack() {
            @Override
            public void onFriendsFound(List<Friend> friends) {
                synchronized (friendsList) {
                    friends.removeAll(friendsList);
                    friendsList.addAll(friends);
                    mFriendRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void addListerToFindFriendsButton() {
        Button findFriendsBtn = findViewById(R.id.findFriendsBtn);
        findFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findFriendsI = new Intent(MyFriendsActivity.this, SearchFriendActivity.class);
                startActivity(findFriendsI);
            }
        });
    }


    public void addListenerToBackButton() {
        ImageButton mBackButton = findViewById(R.id.backFromMyFriends);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(MyFriendsActivity.this);
            }
        });
    }


}