package com.imagixit.higherstudies.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagixit.higherstudies.Interface.ItemClickListner;
import com.imagixit.higherstudies.R;

public class UniversityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView university_name,university_rating;
    public ImageView cartuniversity_image;
    public ItemClickListner listner;

    public UniversityViewHolder(@NonNull View itemView) {
        super(itemView);

        university_name = itemView.findViewById(R.id.university_name);
        university_rating = itemView.findViewById(R.id.university_rating);
        cartuniversity_image =(ImageView) itemView.findViewById(R.id.university_image);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}
