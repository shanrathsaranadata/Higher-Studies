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

public class MenuActivity extends AppCompatActivity {

    private ImageView gove,semigove,pri,scholorship,intenational,diploma,nvq,other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        gove = findViewById(R.id.govermentiv);
        semigove = findViewById(R.id.semigovermentiv);
        pri = findViewById(R.id.privateiv);
        scholorship = findViewById(R.id.scholarshipsiv);
        intenational = findViewById(R.id.internationaliv);
        diploma = findViewById(R.id.diplomaiv);
        nvq = findViewById(R.id.nvqiv);
        other = findViewById(R.id.otheriv);

        gove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MajorActivity.class);
                intent.putExtra("name", "Government");
                startActivity(intent);

            }
        });

        semigove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MajorActivity.class);
                intent.putExtra("name", "Semi-Government");
                startActivity(intent);

            }
        });

        pri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MajorActivity.class);
                intent.putExtra("name", "Private");
                startActivity(intent);

            }
        });

        scholorship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MajorActivity.class);
                intent.putExtra("name", "Scholarship");
                startActivity(intent);

            }
        });

        intenational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MajorActivity.class);
                intent.putExtra("name", "International");
                startActivity(intent);

            }
        });

        diploma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MajorActivity.class);
                intent.putExtra("name", "Diploma");
                startActivity(intent);

            }
        });

        nvq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MajorActivity.class);
                intent.putExtra("name", "NVQ");
                startActivity(intent);

            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MajorActivity.class);
                intent.putExtra("name", "Other");
                startActivity(intent);

            }
        });

        FloatingActionButton fab = findViewById(R.id.home_fabtn04);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation01);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(MenuActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(MenuActivity.this,UniversityActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(MenuActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(MenuActivity.this,MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}