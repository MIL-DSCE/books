package com.in.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class AltCustomGridMainActivity extends Activity {
    GridView grid;
    String[] books = {
            "Adultery",
            "Disgrace",
            "The Great Gatsby",
            "The Fault in our Stars",
            "To Kill a Mockingbird",
            "Life of Pi",
            "Lord of the Lies"


    } ;
    int[] imageId = {
            R.drawable.adultery,
            R.drawable.disgrace,
            R.drawable.thegreatgatsby,
            R.drawable.thefaultinos,
            R.drawable.tokillamb,
            R.drawable.lifeofpi,
            R.drawable.lordoftheflies,


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_grid);

       // Setting the adapter for populating grid view
        AltCustomGridActivity adapter = new AltCustomGridActivity(AltCustomGridMainActivity.this, books, imageId);


        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(AltCustomGridMainActivity.this, "You Clicked at " +books[+ position], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), SingleBookActivity.class);
                Intent intent = new Intent(getApplicationContext(), SingleBookActivity.class);
                Intent i2 = new Intent(getApplicationContext(), SingleBookActivity.class);

                // Pass image index
                i.putExtra("id", position);
                //Pass text index
                intent.putExtra("id2", position);
                // Pass author name Index
                i2.putExtra("id3", position);
                startActivity(i);



            }
        });

    }

}