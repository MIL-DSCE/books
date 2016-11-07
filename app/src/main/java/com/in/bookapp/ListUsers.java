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
    private String name;
    private int image;

    public ListUsers() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage(){
        return image;
    }

    public void setImage(int image){
        this.image = image;
    }




}
