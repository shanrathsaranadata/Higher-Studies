package com.imagixit.higherstudies;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.imagixit.higherstudies.model.Courses;
import com.imagixit.higherstudies.model.SliderData;
import com.imagixit.higherstudies.viewholder.CoursesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    private Spinner slmajor,sltype;
    private EditText serachbarhome;
    private ImageView serachbarhomebtniv;
    private DatabaseReference coursesref,SlideshowsReference;
    private RecyclerView courseslist;
    private String url1,url2,url3,url4,url5;
    private SliderView sliderView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        slmajor = (Spinner) findViewById(R.id.major_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.major, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slmajor.setAdapter(adapter);

        sltype = (Spinner) findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sltype.setAdapter(adapter2);



        slmajor.setOnItemSelectedListener(this);
        sltype.setOnItemSelectedListener(this);





        coursesref = FirebaseDatabase.getInstance().getReference().child("Courses");
        courseslist=findViewById(R.id.recyclercourses);
        courseslist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        courseslist.setLayoutManager(layoutManager);

        SlideshowsReference = FirebaseDatabase.getInstance().getReference().child("Slideshows");
        sliderView = findViewById(R.id.imageSlider);
        autoplayslideshows();

        serachbarhome = findViewById(R.id.search_bar_home);
        serachbarhomebtniv = findViewById(R.id.search_bar_homebtniv);

        serachbarhomebtniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbarcoursesdata();
            }
        });


        FloatingActionButton fab = findViewById(R.id.home_fabtn01);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation03);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_menu:
                        startActivity(new Intent(HomeActivity.this,MenuActivity.class));
                        break;

                    case R.id.navigation_university:
                        startActivity(new Intent(HomeActivity.this,UniversityActivity.class));
                        break;

                    case R.id.navigation_profile:
                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                        break;

                    case R.id.navigation_more:
                        startActivity(new Intent(HomeActivity.this,MoreActivity.class));
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

    private void autoplayslideshows() {

        SlideshowsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Toast.makeText(HomeActivity.this, "Please wait Slideshows is loading", Toast.LENGTH_SHORT).show();

                    url1 = dataSnapshot.child("slidelink01").getValue().toString();
                    url2 = dataSnapshot.child("slidelink02").getValue().toString();
                    url3 = dataSnapshot.child("slidelink03").getValue().toString();
                    url4 = dataSnapshot.child("slidelink04").getValue().toString();
                    url5 = dataSnapshot.child("slidelink05").getValue().toString();


                    ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
                    sliderDataArrayList.add(new SliderData(url1));
                    sliderDataArrayList.add(new SliderData(url2));
                    sliderDataArrayList.add(new SliderData(url3));
                    sliderDataArrayList.add(new SliderData(url4));
                    sliderDataArrayList.add(new SliderData(url5));
                    com.imagixit.higherstudies.Adapter.SliderAdapter adapter = new com.imagixit.higherstudies.Adapter .SliderAdapter(HomeActivity.this, sliderDataArrayList);
                    sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.setScrollTimeInSec(5);
                    sliderView.setAutoCycle(true);
                    sliderView.startAutoCycle();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


   private void Coursesdata(){

        FirebaseRecyclerOptions<Courses> options=
                new FirebaseRecyclerOptions.Builder<Courses>().setQuery(coursesref.orderByChild("course_name"),Courses.class)
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

                                Intent intent = new Intent(HomeActivity.this, CoursedetailsActivity.class);
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

        String serachcourses = serachbarhome.getText().toString();

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

                                    Intent intent = new Intent(HomeActivity.this, CoursedetailsActivity.class);
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

                                    Intent intent = new Intent(HomeActivity.this, CoursedetailsActivity.class);
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

                                    Intent intent = new Intent(HomeActivity.this, CoursedetailsActivity.class);
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