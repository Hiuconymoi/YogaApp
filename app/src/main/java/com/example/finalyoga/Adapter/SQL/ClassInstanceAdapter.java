package com.example.finalyoga.Adapter.SQL;

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
import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.YogaCourse;

import java.util.ArrayList;

public class ClassInstanceAdapter extends RecyclerView.Adapter<ClassInstanceAdapter.ViewHolder> {

    private  ArrayList<ClassInstance> classInstancesList;
    private  IClickItemListener iClickItemListener;
    private ArrayList<YogaCourse> yogaCoursesList;

    public ClassInstanceAdapter(ArrayList<ClassInstance> classInstancesList,
                                ArrayList<YogaCourse> yogaCoursesList,
                                IClickItemListener iClickItemListener){
        this.classInstancesList=classInstancesList;
        this.yogaCoursesList=yogaCoursesList;
        this.iClickItemListener=iClickItemListener;
    }

    @NonNull
    @Override
    public ClassInstanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_instance_item, parent, false);
        return new ClassInstanceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassInstanceAdapter.ViewHolder holder, int position) {
        ClassInstance classInstance=classInstancesList.get(position);
        if (classInstance == null) return;

        YogaCourse yogaCourse= getYogaCourseById(classInstance.getCourse_id());

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

        holder.card_item_class_instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemListener.onClickItem(classInstance);
            }
        });

        holder.txt_class_instance_course.setText("Course: "+ yogaCourse.getCourseName());
        holder.txt_day_of_week.setText("DayOfWeek: "+yogaCourse.getDayOfWeek());
        holder.txt_date_classInstance.setText("Date: "+classInstance.getDate_classInstance());
        holder.txt_teacher_name.setText("Teacher: "+classInstance.getTeacher_name());
    }

    private YogaCourse getYogaCourseById(int courseId) {
        for(YogaCourse yogaCourse: yogaCoursesList){
            if(yogaCourse.getCourseId()==courseId){
                return yogaCourse;
            }
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return classInstancesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_class_instance_course, txt_date_classInstance,
                txt_day_of_week,txt_teacher_name;
        private ImageView img_type_of_class;
        private CardView card_item_class_instance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_class_instance_course=itemView.findViewById(R.id.txt_class_instance_course);
            txt_date_classInstance=itemView.findViewById(R.id.txt_date_classInstance);
            txt_day_of_week=itemView.findViewById(R.id.txt_day_of_week);
            txt_teacher_name=itemView.findViewById(R.id.txt_teacher_name);
            img_type_of_class=itemView.findViewById(R.id.img_type_of_class);
            card_item_class_instance=itemView.findViewById(R.id.card_item_class_instance);
        }
    }
}
