package com.in.bookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {

        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(385, 385));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(16, 16, 16, 16);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.adultery, R.drawable.wutheringheights,
            R.drawable.janeeyre, R.drawable.lifeofpi,
            R.drawable.prideandp, R.drawable.lordoftheflies,
            R.drawable.disgrace, R.drawable.greatexp,
            R.drawable.thefaultinos, R.drawable.thegreatgatsby,
    };

    // Keep all texts in string
    public String[] mStringIds = {
            "Adultery", "Wuthering Heights",
            "Jane Eyre", "Life of Pi",
            "Pride and Prejudice", "Lord of the Lies",
            "Disgrace", "Great Expectations",
            "The Fault in our Stars", "The Great Gatsby"
    };

    public String[] mString2Ids = {
            "Paulo Coehlo", "Emily Bronte",
            "Charlotte Bronte", "Yann Martel",
            "Jane Austen", "WilliamGolding",
            "J.M.Coetzee", "Charles Dickens",
            "John Green", "F.Scott Fitzgerald"
    };

    public String[] mStringSynopsisid = {
            "It is a book written by Paulo Coehlo",
            "It is a book written by Emily Bronte",
            "It is a book written by Charlotte Bronte",
            "It is a book written by Yann Martel",
            "It is a book written by Jane Austen",
            "It is a book written by William Golding",
            "It is a book written by J.M.Coetzee",
            "It is a book written by Charles Dickens",
            "It is a book written by John Green",
            "It is a book written by F.Scott Fitzgerald"
    };
}