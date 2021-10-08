package com.imagixit.higherstudies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MoreActivity extends AppCompatActivity {

    private ImageView mycourses,notifications,inbox,aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        mycourses = findViewById(R.id.mycoursesiv);
        notifications = findViewById(R.id.notificationsiv);
        inbox = findViewById(R.id.inboxiconiv);
        aboutus = findViewById(R.id.aboutusiv);

        mycourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreActivity.this,MoreActivity.class));
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreActivity.this,MoreActivity.class));

            }
        });

        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoreActivity.this,MoreActivity.class));

            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActivity.this, HigherstudiesActivity.class);
                intent.putExtra("link","https://www.shanrathsarana.com");
                startActivity(intent);

            }
        });

        FloatingActionButton fab = findViewById(R.id.home_fabtn05);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MoreActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation05);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(MoreActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(MoreActivity.this,UniversityActivity.class));
                        break;

//                    case R.id.navigation_home:
//                        startActivity(new Intent(MoreActivity.this,HomeActivity.class));
//                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(MoreActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(MoreActivity.this,MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}