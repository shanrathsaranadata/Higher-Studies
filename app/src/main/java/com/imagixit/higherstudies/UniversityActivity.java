package com.imagixit.higherstudies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.imagixit.higherstudies.model.Courses;
import com.imagixit.higherstudies.model.University;
import com.imagixit.higherstudies.viewholder.CoursesViewHolder;
import com.imagixit.higherstudies.viewholder.UniversityViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UniversityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner slcategory;
    private EditText serachbaruniversity;
    private ImageView serachbaruniversitybtniv;
    private DatabaseReference unvercityref;
    private RecyclerView univercitylist;
    RecyclerView.LayoutManager layoutManager;
    private String slectediteam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        unvercityref = FirebaseDatabase.getInstance().getReference().child("University");
        univercitylist=findViewById(R.id.recycleruniversity);
        univercitylist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        univercitylist.setLayoutManager(layoutManager);

        slcategory = (Spinner) findViewById(R.id.spinner_categoryuniversity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slcategory .setAdapter(adapter);

        slcategory.setOnItemSelectedListener(this);

        serachbaruniversity = findViewById(R.id.search_bar_university);
        serachbaruniversitybtniv = findViewById(R.id.search_bar_universitybtniv);

        serachbaruniversitybtniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbaruniversitydata();
            }
        });

        FloatingActionButton fab = findViewById(R.id.home_fabtn07);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(UniversityActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation02);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(UniversityActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(UniversityActivity.this,UniversityActivity.class));
                        break;


                    case R.id.navigation_profile:
                        startActivity(new Intent(UniversityActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(UniversityActivity.this,MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }

    private void Universitydata() {

        FirebaseRecyclerOptions<University> options =
                new FirebaseRecyclerOptions.Builder<University>().setQuery(unvercityref.orderByChild("name"), University.class)
                        .build();
        FirebaseRecyclerAdapter<University, UniversityViewHolder> adapter =
                new FirebaseRecyclerAdapter<University, UniversityViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UniversityViewHolder holder, int position, @NonNull University model) {


                        holder.university_name.setText(model.getName());
                        Picasso.get().load(model.getImage()).into(holder.cartuniversity_image);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(UniversityActivity.this, CoursesActivity.class);
                                intent.putExtra("id", model.getID());
                                startActivity(intent);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.university, viewGroup, false);
                        UniversityViewHolder holder = new UniversityViewHolder(view);
                        return holder;
                    }
                };

        univercitylist.setAdapter(adapter);
        adapter.startListening();
    }
    private void searchbaruniversitydata(){

        String serachuniversity = serachbaruniversity.getText().toString();

        if(TextUtils.isEmpty(serachuniversity)){

            Toast.makeText(this, "Please Write You Want Search", Toast.LENGTH_SHORT).show();
            Universitydata();
        }
        else {

            FirebaseRecyclerOptions<University> options =
                    new FirebaseRecyclerOptions.Builder<University>().setQuery(unvercityref.orderByChild("name").startAt(serachuniversity), University.class)
                            .build();
            FirebaseRecyclerAdapter<University, UniversityViewHolder> adapter =
                    new FirebaseRecyclerAdapter<University, UniversityViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull UniversityViewHolder holder, int position, @NonNull University model) {


                            holder.university_name.setText(model.getName());
                            Picasso.get().load(model.getImage()).into(holder.cartuniversity_image);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(UniversityActivity.this, CoursesActivity.class);
                                    intent.putExtra("id", model.getID());
                                    startActivity(intent);

                                }
                            });

                        }

                        @NonNull
                        @Override
                        public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.university, viewGroup, false);
                            UniversityViewHolder holder = new UniversityViewHolder(view);
                            return holder;
                        }
                    };

            univercitylist.setAdapter(adapter);
            adapter.startListening();
        }

    }

    private void  fliteruniversitydata() {

            FirebaseRecyclerOptions<University> options =
                    new FirebaseRecyclerOptions.Builder<University>().setQuery(unvercityref.orderByChild("type").startAt(slectediteam), University.class)
                            .build();
            FirebaseRecyclerAdapter<University, UniversityViewHolder> adapter =
                    new FirebaseRecyclerAdapter<University, UniversityViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull UniversityViewHolder holder, int position, @NonNull University model) {


                            holder.university_name.setText(model.getName());
                            Picasso.get().load(model.getImage()).into(holder.cartuniversity_image);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(UniversityActivity.this, CoursesActivity.class);
                                    intent.putExtra("id", model.getID());
                                    startActivity(intent);

                                }
                            });

                        }

                        @NonNull
                        @Override
                        public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.university, viewGroup, false);
                            UniversityViewHolder holder = new UniversityViewHolder(view);
                            return holder;
                        }
                    };

            univercitylist.setAdapter(adapter);
            adapter.startListening();
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        slectediteam=parent.getItemAtPosition(position).toString();
        Toast.makeText(UniversityActivity.this, slectediteam, Toast.LENGTH_SHORT).show();

        if(slectediteam.equals("Select Your Type")){

            Toast.makeText(this, "Please Select Your Major", Toast.LENGTH_SHORT).show();
            Universitydata();

        }
        else {

            fliteruniversitydata();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}