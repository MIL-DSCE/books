package com.in.bookapp;

/**
 * Created by RASHI on 12-Oct-16.
 */
public class Fiction {
    //name and address string
    private String email;
    private String password;
    private String username;
    private String single;
    private String multi;
    private String uid;

    public Fiction() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String multi) {
        this.password = multi;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName(){
        return single;
    }
    public void setName(String single){
        this.single = single;
    }
    public String getMulti(){
        return multi;
    }
    public void setMulti(String multi){
        this.multi = multi;
    }
    }

