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
import com.imagixit.higherstudies.viewholder.MajorViewHolder;
import com.squareup.picasso.Picasso;

public class MajorcuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private DatabaseReference majorref;
    private RecyclerView majorlist;
    RecyclerView.LayoutManager layoutManager;
    private String slectediteam;
    private TextView majortitle;
    private String majorid,majorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_majorcu);


        majorlist=findViewById(R.id.recyclermajorcu);
        majorlist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        majorlist.setLayoutManager(layoutManager);
        majorname = getIntent().getStringExtra("name");
        majorid = getIntent().getStringExtra("universitycode");
        majortitle = findViewById(R.id.majorcu_title);

        majortitle.setText(majorname);

        majorref = FirebaseDatabase.getInstance().getReference().child("Major").child(majorname);


        FloatingActionButton fab = findViewById(R.id.home_fabtn12);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MajorcuActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation12);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(MajorcuActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(MajorcuActivity.this,UniversityActivity.class));
                        break;


                    case R.id.navigation_profile:
                        startActivity(new Intent(MajorcuActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(MajorcuActivity.this,MoreActivity.class));
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
                new FirebaseRecyclerOptions.Builder<Major>().setQuery(majorref.orderByChild("type").startAt(majorid), Major.class)
                        .build();
        FirebaseRecyclerAdapter<Major, MajorViewHolder> adapter =
                new FirebaseRecyclerAdapter<Major, MajorViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MajorViewHolder holder, int position, @NonNull Major model) {


                        holder.major_name.setText(model.getUniversity_name());
                        Picasso.get().load(model.getImage()).into(holder.cartmajor_image);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(MajorcuActivity.this, CoursesActivity.class);
                                intent.putExtra("id", model.getUniversity_name());
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
        Toast.makeText(MajorcuActivity.this, slectediteam, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}