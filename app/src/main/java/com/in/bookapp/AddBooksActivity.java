package com.in.bookapp;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Bundle;
import android.os.Environment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;




public class AddBooksActivity extends Activity {
    String[] books = {"Adultery ", "The Fault in our stars", "Disgrace", "Great Expectation", "Jane Eyre", "Life of Pi"};

    Button skip_btn;
    Button profile_btn;
    private AutoCompleteTextView text;
    private MultiAutoCompleteTextView text1;
    private Button save_btn;
    String category1, sub_cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        Firebase.setAndroidContext(this);
        text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        text1 = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
        save_btn = (Button) findViewById(R.id.add_database_button);







        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // variable defination
                category1 = "mybooks";
                sub_cat = "book-i-own";

                //Creating firebase object
                Firebase ref = new Firebase("https://bookapp-c0f06.firebaseio.com/"+category1+"/"+sub_cat);


                //Getting values to store
                String single = text.getText().toString().trim();
                String multi = text1.getText().toString().trim();

                //Creating Fiction object
                final Fiction fiction = new Fiction();

                //Adding values
                fiction.setName(single);
                fiction.setMulti(multi);

                //Storing values to firebase
                ref.child("Fiction").setValue(fiction);


            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, books);

        text.setAdapter(adapter);
        text.setThreshold(1);

        text1.setAdapter(adapter);
        text1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // Skip to book browsing
        skip_btn = (Button) findViewById(R.id.skip_button);
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddBooksActivity.this, Main4Activity.class));
            }
        });


        // Skip to profile page
        profile_btn = (Button) findViewById(R.id.profile_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddBooksActivity.this, ProfileActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
