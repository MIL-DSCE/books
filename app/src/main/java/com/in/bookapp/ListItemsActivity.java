package com.in.bookapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListItemsActivity extends AppCompatActivity {
    String[] books = {"Adultery ", "The Fault in our stars", "Disgrace", "Great Expectation", "Jane Eyre", "Life of Pi"};

    private final String TAG = "ListActivity";

    DatabaseReference mDB;
    DatabaseReference mListItemRef;
    private RecyclerView mListItemsRecyclerView;
    private ListItemsAdapter mAdapter;
    private ArrayList<ListItem> myListItems;
    private FirebaseAuth auth;
    private String uid;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        mDB= FirebaseDatabase.getInstance().getReference();
        mListItemRef = mDB.child(uid).child("Books You Own");
        myListItems = new ArrayList<>();
        mListItemsRecyclerView = (RecyclerView)findViewById(R.id.listItem_recycler_view);
        mListItemsRecyclerView.addItemDecoration(new SimpleDividerItemActivity(getResources()));
        mListItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewListItem();

            }
        });

        mListItemRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Added",dataSnapshot.getValue(ListItem.class).toString());
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Changed",dataSnapshot.getValue(ListItem.class).toString());


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                Log.d(TAG+"Removed",dataSnapshot.getValue(ListItem.class).toString());


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Moved",dataSnapshot.getValue(ListItem.class).toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG+"Cancelled",databaseError.toString());
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
        switch(id){
            case R.id.action_delete_all:
                deleteAllListItems();
                break;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.action_homepage:
                startActivity(new Intent(this, HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    public void createNewListItem() {
        // Create new List Item  at /listItem

        final String key = FirebaseDatabase.getInstance().getReference().child(uid).child("Books You Own").push().getKey();
        LayoutInflater li = LayoutInflater.from(this);
        View getListItemView = li.inflate(R.layout.dialog_get_list_item, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(getListItemView);

        final AutoCompleteTextView userInput = (AutoCompleteTextView) getListItemView.findViewById(R.id.autoCompleteTextView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, books);
        userInput.setAdapter(adapter);
        userInput.setThreshold(1);

        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text

                        String listItemText = userInput.getText().toString();
                        ListItem listItem = new ListItem(listItemText);
                        Map<String, Object> listItemValues = listItem.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/"+uid+"/Books You Own/" + key, listItemValues);
                        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);

                    }
                }).create()
                .show();

    }

    public void deleteAllListItems(){
        FirebaseDatabase.getInstance().getReference().child(uid).child("Books You Own").removeValue();
        myListItems.clear();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Items Deleted Successfully",Toast.LENGTH_SHORT).show();

    }


    private void fetchData(DataSnapshot dataSnapshot) {
        ListItem listItem=dataSnapshot.getValue(ListItem.class);
        myListItems.add(listItem);
        updateUI();
    }


    private void updateUI(){
        mAdapter = new ListItemsAdapter(myListItems);
        mListItemsRecyclerView.setAdapter(mAdapter);
    }

    private class ListItemsHolder extends RecyclerView.ViewHolder{

        public TextView mNameTextView;
        public ListItemsHolder(View itemView){
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.textview_name);

        }

        public void bindData(ListItem s){
            mNameTextView.setText(s.getListItemText());

        }
    }

    private class ListItemsAdapter extends RecyclerView.Adapter<ListItemsHolder>{
        private ArrayList<ListItem> mListItems;
        public ListItemsAdapter(ArrayList<ListItem> ListItems){
            mListItems = ListItems;

        }


        @Override
        public ListItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ListItemsActivity.this);
            View view = layoutInflater.inflate(R.layout.category_list_item_1,parent,false);
            return new ListItemsHolder(view);

        }

        @Override
        public void onBindViewHolder(ListItemsHolder holder, int position) {

            ListItem s = mListItems.get(position);
            holder.bindData(s);

        }

        @Override
        public int getItemCount() {
            return mListItems.size();

        }
    }


}
