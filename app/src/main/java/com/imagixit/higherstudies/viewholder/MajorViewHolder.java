package com.imagixit.higherstudies.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imagixit.higherstudies.Interface.ItemClickListner;
import com.imagixit.higherstudies.R;

public class MajorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView major_name,university_name;
    public ImageView cartmajor_image;
    public ItemClickListner listner;

    public MajorViewHolder(@NonNull View itemView) {
        super(itemView);

        university_name = itemView.findViewById(R.id.major_universityname);
        major_name = itemView.findViewById(R.id.major_name);
        cartmajor_image =(ImageView) itemView.findViewById(R.id.major_image);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}
