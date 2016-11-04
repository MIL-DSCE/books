package com.in.bookapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ProfileMain extends AppCompatActivity {

    private EditText ed_name;
    private EditText ed_contact;
    private Button bt_save;
    private String userId, userEmail;
    private ImageView imageView;
    private static final int SELECT_PICTURE = 100;
    private String uid;
    private FirebaseAuth auth;


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
        uid = auth.getCurrentUser().getUid();
        //getting the reference of the views
        imageView = (ImageView) findViewById(R.id.img_view);
        onImageViewClick(); // for selecting an Image from gallery.



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
                    Toast.makeText(ProfileMain.this, "Please provide valid contact details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (contact.startsWith(num)){
                    Toast.makeText(ProfileMain.this, "Please provide valid contact details.", Toast.LENGTH_SHORT).show();
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

                StorageReference myfileRef = storageRef.child(uid).child("profilePic.jpg");
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = myfileRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(ProfileMain.this, "TASK FAILED", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ProfileMain.this, "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                        Uri downloadUrl =taskSnapshot.getDownloadUrl();
                        String DOWNLOAD_URL = downloadUrl.getPath();
                        Toast.makeText(ProfileMain.this, "Restart the App to view changes to your profile picture", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        System.out.println(taskSnapshot.getBytesTransferred());
                    }
                });




                        }


        });

    }

    // creating an instance of Firebase Storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //creating a storage reference. Replace the below URL with your Firebase storage URL.
    StorageReference storageRef = storage.getReferenceFromUrl("gs://bookapp-c0f06.appspot.com/");

    protected  void onImageViewClick(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"),SELECT_PICTURE );
            }
        });

    }
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("IMAGE PATH TAG", "Image Path : " + path);
                    // Set the image in ImageView
                    imageView.setImageURI(selectedImageUri);

                }
            }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }





    }



