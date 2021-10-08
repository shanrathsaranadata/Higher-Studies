package com.imagixit.higherstudies.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagixit.higherstudies.Interface.ItemClickListner;
import com.imagixit.higherstudies.R;

public class CoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView courses_name,courses_rating,courses_university,courses_description;
    public ImageView cartcourses_image;
    public ItemClickListner listner;

    public CoursesViewHolder(@NonNull View itemView) {
        super(itemView);

        courses_name = itemView.findViewById(R.id.course_name);
        courses_rating = itemView.findViewById(R.id.course_rating);
        courses_university =itemView.findViewById(R.id.course_university);
        courses_description=itemView.findViewById(R.id.course_description);
        cartcourses_image=itemView.findViewById(R.id.course_image);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}
