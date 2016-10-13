package com.in.bookapp;

/**
 * Created by RASHI on 12-Oct-16.
 */
public class Fiction {
    //name and address string
    private String single;
    private String multi;

    public Fiction() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getName() {
        return single;
    }

    public void setName(String single) {
        this.single = single;
    }

    public String getMulti() {
        return multi;
    }

    public void setMulti(String multi) {
        this.multi = multi;
    }
}
