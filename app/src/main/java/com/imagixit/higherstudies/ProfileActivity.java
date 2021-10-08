package com.imagixit.higherstudies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profileimage;
    private TextView editprofile,signout;
    private EditText fullnameet,addresset,mobilenoet;
    private TextView emailet,passwordet;
    private Button save;

    private Uri imageuri;
    private String myuri="";
    private StorageTask uploadTask;
    private StorageReference storageReference;
    private String checker="";
    private FirebaseUser firebaseUser;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pictures");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userid = firebaseUser.getUid().toString();

        profileimage =(CircleImageView) findViewById(R.id.profile_image);
        editprofile =(TextView) findViewById(R.id.editprofiletv);
        signout =(TextView) findViewById(R.id.signouttv);
        fullnameet=(EditText) findViewById(R.id.profile_name);
        emailet=(TextView) findViewById(R.id.profile_email);
        mobilenoet=(EditText) findViewById(R.id.profile_phoneno);
        addresset=(EditText) findViewById(R.id.profile_address);
        passwordet=(TextView) findViewById(R.id.profile_password);
        save=(Button) findViewById(R.id.profile_save);

        userInfoDisplay();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.equals("clicked")){

                    userInfosaved();

                }
                else {
                    onlyuserInfo();
                }
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checker ="clicked";
                CropImage.activity(imageuri)
                        .setAspectRatio(1,1)
                        .start(ProfileActivity.this);
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_fabtn06);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseAuth mauth;
                mauth = FirebaseAuth.getInstance();
                mauth.signOut();

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation04);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(ProfileActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(ProfileActivity.this,UniversityActivity.class));
                        break;

//                    case R.id.navigation_home:
//                        startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
//                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(ProfileActivity.this,MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data != null){

            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            imageuri = result.getUri();
            profileimage.setImageURI(imageuri);

        }
        else{
            Toast.makeText(this, "Error Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
            finish();
        }
    }

    private void onlyuserInfo() {



        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        HashMap<String,Object> usermap = new HashMap<>();
        usermap.put("name",fullnameet.getText().toString());
        usermap.put("email",emailet.getText().toString());
        usermap.put("address",addresset.getText().toString());
        usermap.put("phone",mobilenoet.getText().toString());
        userref.updateChildren(usermap);

        Toast.makeText(ProfileActivity.this, "Profile Info Update is Complete!", Toast.LENGTH_SHORT).show();
    }

    private void userInfosaved() {

        if(TextUtils.isEmpty(fullnameet.getText().toString())){

            Toast.makeText(this, "Name is Empty...", Toast.LENGTH_SHORT).show();

        }


        else if(TextUtils.isEmpty(emailet.getText().toString())){

            Toast.makeText(this, "Email is Empty...", Toast.LENGTH_SHORT).show();

        }

        else if(TextUtils.isEmpty(mobilenoet.getText().toString())){

            Toast.makeText(this, "Mobileno is Empty...", Toast.LENGTH_SHORT).show();

        }


        else if(TextUtils.isEmpty(addresset.getText().toString())){

            Toast.makeText(this, "Address is Empty...", Toast.LENGTH_SHORT).show();

        }


        else if(checker.equals("clicked")){

            uploadImage();

        }
    }

    private void uploadImage() {

        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait,while we are updating your account info");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imageuri != null){

            final StorageReference fileref = storageReference
                    .child(userid+".jpg");

            uploadTask =fileref.putFile(imageuri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){
                        throw task.getException();
                    }


                    return fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            if(task.isSuccessful()){
                                Uri downloadUri =task.getResult();
                                myuri =downloadUri.toString();


                                DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                                HashMap<String,Object>usermap = new HashMap<>();
                                usermap.put("name",fullnameet.getText().toString());
                                usermap.put("email",emailet.getText().toString());
                                usermap.put("address",addresset.getText().toString());
                                usermap.put("phone",mobilenoet.getText().toString());
                                usermap.put("image",myuri);

                                userref.updateChildren(usermap);

                                progressDialog.dismiss();

                                startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
                                Toast.makeText(ProfileActivity.this, "Profile Info Update is Sucess!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
        else{
            Toast.makeText(this, "Image is Null", Toast.LENGTH_SHORT).show();
        }

    }

    private void userInfoDisplay() {

        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if(dataSnapshot.child("image").exists()){

                        String image =dataSnapshot.child("image").getValue().toString();
                        String name =dataSnapshot.child("name").getValue().toString();
                        String phone=dataSnapshot.child("phone").getValue().toString();
                        String address =dataSnapshot.child("address").getValue().toString();
                        String email =dataSnapshot.child("email").getValue().toString();
                        String password =dataSnapshot.child("password").getValue().toString();

                        Picasso.get().load(image).into(profileimage);
                        fullnameet.setText(name);
                        emailet.setText(email);
                        addresset.setText(address);
                        mobilenoet.setText(phone);
                        passwordet.setText(password);
                    }
                    else if(dataSnapshot.child("name").exists()){

                        String name =dataSnapshot.child("name").getValue().toString();
                        String phone=dataSnapshot.child("phone").getValue().toString();
                        String address =dataSnapshot.child("address").getValue().toString();
                        String email =dataSnapshot.child("email").getValue().toString();
                        String password =dataSnapshot.child("password").getValue().toString();

                        fullnameet.setText(name);
                        emailet.setText(email);
                        addresset.setText(address);
                        mobilenoet.setText(phone);
                        passwordet.setText(password);

                        Toast.makeText(ProfileActivity.this, "Please upload your profile picture complete your account", Toast.LENGTH_SHORT).show();

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}