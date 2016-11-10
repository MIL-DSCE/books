package com.in.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private String userName;
    private TextView userDisplayName, userDisplayContact, userDisplayEmail;
    FirebaseAuth auth;
    private  String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userName = extras.getString("userDisplayName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(userName);

        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();

        userDisplayName = (TextView) findViewById(R.id.textView_dpName);
        userDisplayEmail = (TextView) findViewById(R.id.textView_dpEmail);
        userDisplayContact = (TextView) findViewById(R.id.textView_dpContact);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        Firebase ref = new Firebase("https://bookapp-c0f06.firebaseio.com/users/");
        final Firebase firebaseRef = ref.child(userName).child("profile-detail").child("name");
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                userDisplayName.setText(name);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        final Firebase firebaseRef2 = ref.child(userName).child("profile-detail").child("email");
        firebaseRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                userDisplayEmail.setText(email);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        final Firebase firebaseRef3 = ref.child(userName).child("profile-detail").child("contact");
        firebaseRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String contact = dataSnapshot.getValue(String.class);
                userDisplayContact.setText(contact);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
