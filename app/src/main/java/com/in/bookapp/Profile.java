package com.in.bookapp;

/**
 * Created by RASHI on 15-Oct-16.
 */
public class Profile {

    private String name;
    private String contact;

    public Profile(){
        // left empty
    }

    //getters and setters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getContact(){
        return contact;
    }
    public void setContact(String contact){
        this.contact = contact;
    }

}
