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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.imagixit.higherstudies.model.Courses;
import com.imagixit.higherstudies.viewholder.CoursesViewHolder;
import com.squareup.picasso.Picasso;

public class CoursesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner slmajor,sltype;
    private EditText serachbarcourses;
    private ImageView serachbarcoursesbtniv;
    private DatabaseReference coursesref;
    private RecyclerView courseslist;
    private String courseid;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        coursesref = FirebaseDatabase.getInstance().getReference().child("Courses");
        courseslist=findViewById(R.id.recyclerCourses);
        courseslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        courseslist.setLayoutManager(layoutManager);

        courseid=getIntent().getStringExtra("id");

        slmajor = (Spinner) findViewById(R.id.major_spinner_courses);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.major, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slmajor.setAdapter(adapter);

        sltype = (Spinner) findViewById(R.id.type_spinner_courses);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sltype.setAdapter(adapter2);



        slmajor.setOnItemSelectedListener(this);
        sltype.setOnItemSelectedListener(this);

        serachbarcourses = findViewById(R.id.search_bar_courses);
        serachbarcoursesbtniv = findViewById(R.id.search_bar_coursesbtniv);

        serachbarcoursesbtniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbarcoursesdata();
            }
        });



        FloatingActionButton fab = findViewById(R.id.home_fabtn09);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CoursesActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation09);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(CoursesActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(CoursesActivity.this,UniversityActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(CoursesActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(CoursesActivity.this,MoreActivity.class));
                        break;

                }
                return false;
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        Coursesdata();


    }

    private void Coursesdata(){

        FirebaseRecyclerOptions<Courses> options=
                new FirebaseRecyclerOptions.Builder<Courses>().setQuery(coursesref.orderByChild("universities").equalTo(courseid),Courses.class)
                        .build();
        FirebaseRecyclerAdapter<Courses, CoursesViewHolder> adapter=
                new FirebaseRecyclerAdapter<Courses, CoursesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Courses model) {

                        holder.courses_name.setText(model.getCourse_name());
                        holder.courses_university.setText(model.getUniversities());
                        Picasso.get().load(model.getImage()).into(holder.cartcourses_image);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(CoursesActivity.this, CoursedetailsActivity.class);
                                intent.putExtra("id", model.getCourse_name());
                                startActivity(intent);

                            }
                        });

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

    private void searchbarcoursesdata(){

        String serachcourses = serachbarcourses.getText().toString();

        if(TextUtils.isEmpty(serachcourses)){

            Toast.makeText(this, "Please Write You Want Search", Toast.LENGTH_SHORT).show();
            Coursesdata();
        }
        else {

            FirebaseRecyclerOptions<Courses> options =
                    new FirebaseRecyclerOptions.Builder<Courses>().setQuery(coursesref.orderByChild("course_name").startAt(serachcourses), Courses.class)
                            .build();
            FirebaseRecyclerAdapter<Courses, CoursesViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Courses, CoursesViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Courses model) {

                            holder.courses_name.setText(model.getCourse_name());
                            holder.courses_university.setText(model.getUniversities());
                            Picasso.get().load(model.getImage()).into(holder.cartcourses_image);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(CoursesActivity.this, CoursedetailsActivity.class);
                                    intent.putExtra("id", model.getCourse_name());
                                    startActivity(intent);

                                }
                            });


                        }

                        @NonNull
                        @Override
                        public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.courses, viewGroup, false);
                            CoursesViewHolder holder = new CoursesViewHolder(view);
                            return holder;
                        }
                    };

            courseslist.setAdapter(adapter);
            adapter.startListening();
        }

    }

    private void flitercoursesdata(){


        if(!slmajor.getSelectedItem().toString().equals("Select Your Major")){



            FirebaseRecyclerOptions<Courses> options=
                    new FirebaseRecyclerOptions.Builder<Courses>().setQuery(coursesref.orderByChild("major").startAt(slmajor.getSelectedItem().toString()),Courses.class)
                            .build();
            FirebaseRecyclerAdapter<Courses, CoursesViewHolder> adapter=
                    new FirebaseRecyclerAdapter<Courses, CoursesViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Courses model) {

                            holder.courses_name.setText(model.getCourse_name());
                            holder.courses_university.setText(model.getUniversities());
                            Picasso.get().load(model.getImage()).into(holder.cartcourses_image);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(CoursesActivity.this, CoursedetailsActivity.class);
                                    intent.putExtra("id", model.getCourse_name());
                                    startActivity(intent);

                                }
                            });

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
        if(!sltype.getSelectedItem().toString().equals("Select Your Type")){

            FirebaseRecyclerOptions<Courses> options=
                    new FirebaseRecyclerOptions.Builder<Courses>().setQuery(coursesref.orderByChild("type").startAt(sltype.getSelectedItem().toString()),Courses.class)
                            .build();
            FirebaseRecyclerAdapter<Courses, CoursesViewHolder> adapter=
                    new FirebaseRecyclerAdapter<Courses, CoursesViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CoursesViewHolder holder, int position, @NonNull Courses model) {

                            holder.courses_name.setText(model.getCourse_name());
                            holder.courses_university.setText(model.getUniversities());
                            Picasso.get().load(model.getImage()).into(holder.cartcourses_image);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(CoursesActivity.this, CoursedetailsActivity.class);
                                    intent.putExtra("id", model.getCourse_name());
                                    startActivity(intent);

                                }
                            });

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        Toast.makeText(this, slmajor.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, sltype.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        flitercoursesdata();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}