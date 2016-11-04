package com.in.bookapp;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListUsersActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    DatabaseReference mSingleUserRef;
    private RecyclerView mListUsersRecyclerView;
    private ListUsersAdapter mUsersAdapter;
    private ArrayList<ListUsers> mListUser;
    private FirebaseAuth auth;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bookapp-c0f06.firebaseio.com/");
        mSingleUserRef = dbRef.child("users");
        mListUser = new ArrayList<>();
        mListUsersRecyclerView = (RecyclerView) findViewById(R.id.listUser_recycler_view);
        mListUsersRecyclerView.addItemDecoration(new SimpleDividerItemActivity(getResources()));
        mListUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

        mSingleUserRef.child("users").orderByChild("name").equalTo("Test").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("UsersActivity Added",dataSnapshot.getValue(ListUsers.class).toString());
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        
        

    }

    private void fetchData(DataSnapshot dataSnapshot) {
        ListUsers listUser = dataSnapshot.getValue(ListUsers.class);
        mListUser.add(listUser);
        updateUI();
    }

    private void updateUI() {
        mUsersAdapter = new ListUsersAdapter(mListUser);
        mListUsersRecyclerView.setAdapter(mUsersAdapter);
    }

    private class ListUsersHolder extends RecyclerView.ViewHolder {

        private TextView mUserNameTextView;

        public ListUsersHolder(View itemView) {
            super(itemView);

            mUserNameTextView = (TextView) itemView.findViewById(R.id.textview_user);

        }

        public void bindData(ListUsers string) {
            mUserNameTextView.setText(string.getDisplayName());

        }
    }

    private class ListUsersAdapter extends RecyclerView.Adapter<ListUsersHolder> {
        private ArrayList<ListUsers> mListUsers;

        public ListUsersAdapter(ArrayList<ListUsers> ListUsers) {
            mListUsers = ListUsers;

        }

        @Override
        public ListUsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ListUsersActivity.this);
            View view = layoutInflater.inflate(R.layout.category_list_user_1,parent,false);
            return new ListUsersHolder(view);
        }

        @Override
        public void onBindViewHolder(ListUsersHolder holder, int position) {
            ListUsers s = mListUsers.get(position);
            holder.bindData(s);

        }

        @Override
        public int getItemCount() {
            return mListUsers.size();
        }
    }
}
