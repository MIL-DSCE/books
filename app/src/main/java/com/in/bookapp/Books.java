
package com.in.bookapp;

import java.net.URL;

public class Books {
    private String title, author, year;
    private URL imageUrl;

    public Books() {
    }

    public Books(String title, String genre, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public URL getImageUrl() {return imageUrl;}
    public void setImageUrl(URL imageUrl){this.imageUrl = imageUrl; }
}
