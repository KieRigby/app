package com.example.bountyhunterapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FriendList {
    //Maps to the list of fruit objects
    @SerializedName("users")
    private List<Friend> friends;

    /**
     * @return list of fruit objects
     */
    public List<Friend> getFriends() {
        return this.friends;
    }
}
