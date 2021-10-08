package com.imagixit.higherstudies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.imagixit.higherstudies.model.Courses;
import com.imagixit.higherstudies.viewholder.CoursesViewHolder;
import com.squareup.picasso.Picasso;

public class HigherstudiesActivity extends AppCompatActivity {

    private WebView higherstudies;
    private String higherstudieslink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_higherstudies);

        higherstudies =(WebView) findViewById(R.id.higherstudieswebview);
        higherstudieslink=getIntent().getStringExtra("link");


        FloatingActionButton fab = findViewById(R.id.home_fabtn10);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(HigherstudiesActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation10);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(HigherstudiesActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(HigherstudiesActivity.this,UniversityActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(HigherstudiesActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(HigherstudiesActivity.this,MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        WebSettings webSettings = higherstudies.getSettings();

        webSettings.setJavaScriptEnabled(true);

        higherstudies.loadUrl(higherstudieslink);

        higherstudies.setWebViewClient(new WebViewClient());

        Toast.makeText(HigherstudiesActivity.this, "Please Wait to loading mobile view", Toast.LENGTH_SHORT).show();

    }
}