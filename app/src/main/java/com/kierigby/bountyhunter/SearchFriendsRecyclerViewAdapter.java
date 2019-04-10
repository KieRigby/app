package com.kierigby.bountyhunter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.User;
import java.util.List;

public class SearchFriendsRecyclerViewAdapter extends RecyclerView.Adapter<SearchFriendsRecyclerViewAdapter.MyViewHolder>{

    private static List<User> mSearchFriendList;
    private static BountyHunterAPI api;

    SearchFriendsRecyclerViewAdapter(List<User> users) {
        mSearchFriendList = users;
    }

    /**
     * Provide a reference to the views for each data item
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton addBtn;

        /**
         * Sets up the view for each data item,
         *
         * @param itemView the view that will be used by the item
         */
        MyViewHolder(final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.addFriendName);
            api = new BountyHunterAPI(itemView.getContext());
            addBtn = itemView.findViewById(R.id.addFriendsIcon);
        }
    }

    @NonNull
    @Override
    public SearchFriendsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_friends_item, viewGroup, false);
        return new SearchFriendsRecyclerViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFriendsRecyclerViewAdapter.MyViewHolder myViewHolder, final int i){

        User friendObj = mSearchFriendList.get(i);
        String username = friendObj.getUsername();
        myViewHolder.textView.setText(username);
        myViewHolder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User friend2Add = mSearchFriendList.get(i);
                api.addFriend(friend2Add.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSearchFriendList.size();
    }

}
