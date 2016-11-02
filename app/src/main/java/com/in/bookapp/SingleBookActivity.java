package com.in.bookapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleBookActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_book);

        // Get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);

        ImageView imageView = (ImageView) findViewById(R.id.SingleView);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);

        // Selected image text

        TextView tv1 = (TextView) findViewById(R.id.tv_title);
        tv1.setText(imageAdapter.mStringIds[+ position]);

        // Selected image author name

        TextView tv2 = (TextView) findViewById(R.id.tv_author);
        tv2.setText(imageAdapter.mString2Ids[+ position]);


        // Synopsis

        TextView tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setText(imageAdapter.mStringSynopsisid[+ position]);








    }


}