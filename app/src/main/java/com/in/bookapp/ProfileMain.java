package com.in.bookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileMain extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_contact;
    private Button bt_save;
    private FirebaseAuth auth;
    private String userId, userEmail;
    String name_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_profile);


        auth = FirebaseAuth.getInstance();

        Firebase.setAndroidContext(this);
        ed_name = (EditText) findViewById(R.id.editText3);
        ed_contact = (EditText) findViewById(R.id.editText4);
        bt_save = (Button) findViewById(R.id.save_button);
        userId = auth.getCurrentUser().getUid();
        userEmail = auth.getCurrentUser().getEmail();


        ed_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                bt_save.setEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()>0){
                    bt_save.setEnabled(true);
                } else {
                    bt_save.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Profile profile = new Profile();
                Firebase ref = new Firebase("https://bookapp-c0f06.firebaseio.com/users/" + userId);
                String name = ed_name.getText().toString().trim();
                String contact = ed_contact.getText().toString().trim();
                int numbers[] = {1,2,3,4,5,6,10};
                String num = numbers.toString();



                if (TextUtils.isEmpty(name)){
                    Toast.makeText(ProfileMain.this, "The name field cannot be left empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contact)){
                    Toast.makeText(ProfileMain.this, "The contact field cannot be left empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contact.length()<10 || contact.length()>10) {
                    Toast.makeText(ProfileMain.this, "Please provide a valid contact detail.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contact.startsWith(num)){
                    Toast.makeText(ProfileMain.this, "Please provide a valid contact details.", Toast.LENGTH_SHORT).show();
                    return;
                }

                profile.setName(name);
                profile.setContact(contact);

                ref.child("Profile Details").setValue(profile);
                ref.child("Profile Details").child("Email ID").setValue(userEmail);
                //Value event listener for realtime data update
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //Getting the data from snapshot
                            Profile profile = postSnapshot.getValue(Profile.class);

                            //Adding it to a string
                            String string = profile.getName();
                            Intent i = new Intent(ProfileMain.this, ProfileActivity.class);
                            i.putExtra("string",string);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });




                        }


        });

    }
}
