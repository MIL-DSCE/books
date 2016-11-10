package com.in.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;



public class ListUsersActivity extends AppCompatActivity {


    ListView UserList;
    Firebase Ref;
    TextView textView;
    String title;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        Firebase.setAndroidContext(this);
        Ref = new Firebase("https://bookapp-c0f06.firebaseio.com/");
        UserList = (ListView) findViewById(R.id.mPeopleList);
        textView = (TextView) findViewById(R.id.tv);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        textView.setText("People who own " + title);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Firebase messagesRef = Ref.child("Books in our Collection").child(title);
        final FirebaseListAdapter<String> adapter = new FirebaseListAdapter<String>(ListUsersActivity.this, String.class, android.R.layout.simple_list_item_1, messagesRef) {
            @Override
            protected void populateView(View view, String s, int i) {

                TextView textView = (TextView)view.findViewById(android.R.id.text1);
                textView.setText(s);
            }
        };
        UserList.setAdapter(adapter);
        UserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = ((TextView)view).getText().toString();
                Intent intent = new Intent(ListUsersActivity.this, UserProfileActivity.class);
                intent.putExtra("userDisplayName",temp );
                startActivity(intent);

            }
        });
    }
}
