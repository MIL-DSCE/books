package com.in.bookapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProfileActivity extends AppCompatActivity {


    Button browse_btn, view_books;
    Button add_books_btn, profile_btn;
    Button signOut;
    Button upload;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private TextView user_name, user_contact;
    private String uid, string;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    private Button mGoogle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);
        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();




        //setting profile picture
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final ImageView image_view = (ImageView) findViewById(R.id.image_view);
        StorageReference storageRef = storage.getReferenceFromUrl("gs://bookapp-c0f06.appspot.com/");
        StorageReference profilePicRef = storageRef.child(uid).child("profilePic.jpg");

        profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(ProfileActivity.this).load(uri).into(image_view);
            }
        });

        //view books button
        view_books = (Button) findViewById(R.id.view_books_btn);
        view_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, BookListActivity.class));
            }
        });


        user_name = (TextView) findViewById(R.id.user_profile_name);




        //Profile Name

        auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        Firebase ref = new Firebase("https://bookapp-c0f06.firebaseio.com/"+uid);
        final DatabaseReference profileRef = database.getReferenceFromUrl("https://bookapp-c0f06.firebaseio.com/"+uid);
        profileRef.orderByChild("Profile Details/name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                String string = profile.getName();
                user_name.setText(string);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                String string = profile.getName();
                user_name.setText(string);

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Setting contact details below profile name
        user_contact = (TextView) findViewById(R.id.tv_contact);
        final DatabaseReference contactRef = database.getReferenceFromUrl("https://bookapp-c0f06.firebaseio.com/"+uid);
        contactRef.orderByChild("Profile Details/contact").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                String string2 = profile.getContact();
                user_contact.setText(string2);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                String string2 = profile.getContact();
                user_contact.setText(string2);

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //Image Upload demo
        upload = (Button) findViewById(R.id.img_upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ImageUploadActivity.class));
            }
        });


        //Edit profile
        profile_btn = (Button) findViewById(R.id.edit_profile_btn);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileMain.class));
            }
        });





        // Browse books
        browse_btn = (Button) findViewById(R.id.browse_button);
        browse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, Main4Activity.class));
            }
        });

        // Adding books
        add_books_btn = (Button) findViewById(R.id.add_books_you_own);
        add_books_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ListItemsActivity.class));
            }
        });

        // Custom sign out
        //get firebase auth instance

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        signOut = (Button) findViewById(R.id.sign_out);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    public void loadlogIn(){
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }


}
