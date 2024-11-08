package com.example.finalyoga.Adapter.SQL;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyoga.Interface.IClickItemListener;
import com.example.finalyoga.R;
import com.example.finalyoga.database.model.YogaCourse;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final ArrayList<YogaCourse> courseList;
    private final IClickItemListener iItemClickListener;


    public CourseAdapter(ArrayList<YogaCourse> courseList, IClickItemListener iItemClickListener) {
        this.courseList = courseList;
        this.iItemClickListener = iItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YogaCourse yogaCourse = courseList.get(position);
        if (yogaCourse == null) return;

        // Set appropriate image based on the type of class
        switch (yogaCourse.getTypeOfClass()) {
            case "Flow Yoga":
                holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_01);
                break;
            case "Aerial Yoga":
                holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_02);
                break;
            case "Family Yoga":
                holder.img_type_of_class.setImageResource(R.drawable.img_yoga_class_03);
                break;
        }

        holder.card_item_yoga_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iItemClickListener.onClickItem(yogaCourse);
            }
        });

        // Set text fields
        holder.txt_course_name.setText("Course: " + yogaCourse.getCourseName());
        holder.txt_type_of_class.setText("Type: " + yogaCourse.getTypeOfClass());

        // Ensure price formatting with two decimal places
        holder.txt_price_of_course.setText(String.format("Price: $%.2f", yogaCourse.getPricePerClass()));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_course_name, txt_type_of_class, txt_price_of_course;
        private ImageView img_type_of_class;
        private CardView card_item_yoga_course;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_course_name = itemView.findViewById(R.id.txt_course_name);
            txt_type_of_class = itemView.findViewById(R.id.txt_type_of_class);
            txt_price_of_course = itemView.findViewById(R.id.txt_price_of_course);
            img_type_of_class = itemView.findViewById(R.id.img_type_of_class);
            card_item_yoga_course=itemView.findViewById(R.id.card_item_yoga_course);
        }
    }
}
