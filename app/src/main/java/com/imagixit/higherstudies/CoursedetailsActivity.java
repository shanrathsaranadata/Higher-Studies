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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.imagixit.higherstudies.model.Courses;
import com.imagixit.higherstudies.viewholder.CoursesViewHolder;
import com.squareup.picasso.Picasso;

public class CoursedetailsActivity extends AppCompatActivity {

    private String courseid;
    private DatabaseReference coursesref;
    private RecyclerView courseslist;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursedetails);

        coursesref = FirebaseDatabase.getInstance().getReference().child("Courses");
        courseslist=findViewById(R.id.recyclercoursedettails);
        courseslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        courseslist.setLayoutManager(layoutManager);

        courseid=getIntent().getStringExtra("id");

        FloatingActionButton fab = findViewById(R.id.home_fabtn08);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CoursedetailsActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation08);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(CoursedetailsActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(CoursedetailsActivity.this,UniversityActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(CoursedetailsActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(CoursedetailsActivity.this,MoreActivity.class));
                        break;

                }
                return false;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Courses> options=
                new FirebaseRecyclerOptions.Builder<Courses>().setQuery(coursesref.orderByChild("course_name").equalTo(courseid),Courses.class)
                        .build();
        FirebaseRecyclerAdapter<Courses, CoursesViewHolder> adapter=
                new FirebaseRecyclerAdapter<Courses, CoursesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Courses model) {

                        holder.courses_name.setText(model.getCourse_name());
                        holder.courses_university.setText(model.getUniversities());
                        holder.courses_description.setText(model.getDescription());
                        Picasso.get().load(model.getImage()).into(holder.cartcourses_image);

                    }

                    @NonNull
                    @Override
                    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.courses,viewGroup,false);
                        CoursesViewHolder holder =new CoursesViewHolder(view);
                        return holder;
                    }
                };

        courseslist.setAdapter(adapter);
        adapter.startListening();
    }
}