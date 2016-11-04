package com.in.bookapp;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by RASHI on 04-Nov-16.
 */
public class ListUsers implements Serializable {
    private String displayName;
    private String uid;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    public ListUsers() {

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("displayName", displayName);
        return result;

    }
}
