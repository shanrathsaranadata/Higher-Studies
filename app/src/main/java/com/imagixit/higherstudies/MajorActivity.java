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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.imagixit.higherstudies.model.Major;
import com.imagixit.higherstudies.model.University;
import com.imagixit.higherstudies.viewholder.MajorViewHolder;
import com.imagixit.higherstudies.viewholder.UniversityViewHolder;
import com.squareup.picasso.Picasso;

public class MajorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private DatabaseReference majorref;
    private RecyclerView majorlist;
    RecyclerView.LayoutManager layoutManager;
    private String slectediteam;
    private TextView majortitle;
    private String majorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);



        majorlist=findViewById(R.id.recyclermajor);
        majorlist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        majorlist.setLayoutManager(layoutManager);
        majorid = getIntent().getStringExtra("name");
        majortitle = findViewById(R.id.major_title);

        majortitle.setText(majorid);
        majorref = FirebaseDatabase.getInstance().getReference().child("Major").child("Main");


        FloatingActionButton fab = findViewById(R.id.home_fabtn11);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MajorActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation11);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(MajorActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(MajorActivity.this,UniversityActivity.class));
                        break;


                    case R.id.navigation_profile:
                        startActivity(new Intent(MajorActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(MajorActivity.this,MoreActivity.class));
                        break;

                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Major> options =
                new FirebaseRecyclerOptions.Builder<Major>().setQuery(majorref.orderByChild("type").equalTo(majorid), Major.class)
                        .build();
        FirebaseRecyclerAdapter<Major, MajorViewHolder> adapter =
                new FirebaseRecyclerAdapter<Major, MajorViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MajorViewHolder holder, int position, @NonNull Major model) {


                        holder.major_name.setText(model.getName());
                        Picasso.get().load(model.getImage()).into(holder.cartmajor_image);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(MajorActivity.this, MajorcuActivity.class);
                                intent.putExtra("name", model.getName());
                                intent.putExtra("id", majorid);
                                startActivity(intent);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public MajorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.major, viewGroup, false);
                        MajorViewHolder holder = new MajorViewHolder(view);
                        return holder;
                    }
                };

        majorlist.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        slectediteam=parent.getItemAtPosition(position).toString();
        Toast.makeText(MajorActivity.this, slectediteam, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}