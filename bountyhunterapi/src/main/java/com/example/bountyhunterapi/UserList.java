package com.example.bountyhunterapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList {
    //Maps to the list of fruit objects
    @SerializedName("users")
    private List<User> users;

    /**
     * @return list of fruit objects
     */
    public List<User> getUsers() {
        return this.users;
    }
}
