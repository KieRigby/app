package com.kierigby.bountyhunter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.Friend;

import java.util.List;

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.MyViewHolder> {

    private static List<Friend> mFriendList;
    private static BountyHunterAPI api;

    public FriendsRecyclerViewAdapter(List<Friend> friends) {
        mFriendList = friends;
    }

    /**
     * Provide a reference to the views for each data item
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton deleteBtn;

        /**
         * Sets up the view for each data item,
         *
         * @param itemView the view that will be used by the item
         */
        MyViewHolder(final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.myFriendName);
            api = new BountyHunterAPI(itemView.getContext());
            deleteBtn = itemView.findViewById(R.id.deleteFriendsIcon);
        }

    }

    @NonNull
    @Override
    public FriendsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.friend_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendsRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {

        Friend friendObj = mFriendList.get(i);
        String username = friendObj.getFriend().getUsername();
        myViewHolder.textView.setText(username);
        myViewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend2Delete = mFriendList.get(myViewHolder.getAdapterPosition());
                api.removeFriend(friend2Delete.getFriend().getId(), new BountyHunterAPI.successCallBack() {
                    @Override
                    public void success() {
                        mFriendList.remove(myViewHolder.getAdapterPosition());
                        notifyItemRemoved(myViewHolder.getAdapterPosition());
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

}
