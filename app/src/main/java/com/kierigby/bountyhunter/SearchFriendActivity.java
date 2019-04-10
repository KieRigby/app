package com.kierigby.bountyhunter;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchFriendActivity extends AppCompatActivity {

    private final List<User> searchFriendsList = Collections.synchronizedList(new ArrayList<User>());
    private BountyHunterAPI api;
    private SearchFriendsRecyclerViewAdapter mSearchFriendRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GlobalUser) this.getApplication()).tokenCheck();
        setContentView(R.layout.activity_search_friend);
        api = new BountyHunterAPI(this);
        setUpRecyclerView();
        addListenerToBackButton();
        addListenerToSearchEditText();
    }

    private void setUpRecyclerView() {

        RecyclerView mSearchFriendRecyclerView = findViewById(R.id.add_friends_recycler_view);
        mSearchFriendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchFriendRecyclerViewAdapter = new SearchFriendsRecyclerViewAdapter(searchFriendsList);
        mSearchFriendRecyclerView.setAdapter(mSearchFriendRecyclerViewAdapter);

    }

    public void addListenerToBackButton() {
        ImageButton mBackButton = findViewById(R.id.backArrowFromSearchFriends);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(SearchFriendActivity.this);
            }
        });
    }

    public void addListenerToSearchEditText() {
        EditText searchEditText = findViewById(R.id.edit_search);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().isEmpty()) {
                    searchFriendsList.clear();
                    mSearchFriendRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    searchFriendsList.clear();
                    mSearchFriendRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    searchFriendsList.clear();
                    mSearchFriendRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    api.searchUser(s.toString(), new BountyHunterAPI.FoundUsersCallBack() {
                        @Override
                        public void onUsersFound(List<User> users) {
                            users.removeAll(searchFriendsList);
                            searchFriendsList.addAll(users);
                            searchFriendsList.remove(((GlobalUser) getApplication()).getLoggedInUser());
                            mSearchFriendRecyclerViewAdapter.notifyDataSetChanged();

                        }
                    });
                }

            }
        });

    }
}
