package com.example.finalyoga.YogaCourseFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyoga.Adapter.SQL.ClassInstanceInClassAdapter;
import com.example.finalyoga.Adapter.SQL.CourseAdapter;
import com.example.finalyoga.ClassInstanceFragment.DetailClassInstanceFragment;
import com.example.finalyoga.HomepageActivity;
import com.example.finalyoga.Interface.IClickItemListener;
import com.example.finalyoga.R;
import com.example.finalyoga.database.DatabaseHelper;
import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.YogaCourse;

import java.util.ArrayList;

public class DetailYogaCourseFragment extends Fragment {

    private static final String ARG_COURSE = "arg_course";

    private Bundle bundleReceive;
    private YogaCourse yogaCourse;
    private DatabaseHelper dbHelper;
    private HomepageActivity homepageActivity;
    private ClassInstanceInClassAdapter classInstanceInClassAdapter;

    public DetailYogaCourseFragment () {}

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_yoga_course, container, false);

        homepageActivity = (HomepageActivity) requireActivity();
        dbHelper = new DatabaseHelper(getContext()); // Initialize dbHelper

        // Bind views
        ImageView img_decoration_detail_course = view.findViewById(R.id.img_decoration_detail_course);
        TextView txt_detail_course_name = view.findViewById(R.id.txt_detail_course_name);
        TextView txt_detail_type_of_class = view.findViewById(R.id.txt_detail_type_of_class);
        TextView txt_detail_day_of_week = view.findViewById(R.id.txt_detail_day_of_week);
        TextView txt_detail_time = view.findViewById(R.id.txt_detail_time);
        TextView txt_detail_capacity = view.findViewById(R.id.txt_detail_capacity);
        TextView txt_detail_duration = view.findViewById(R.id.txt_detail_duration);
        TextView txt_detail_price_per_class = view.findViewById(R.id.txt_detail_price_per_class);
        TextView txt_detail_description = view.findViewById(R.id.txt_detail_description);
        Button btn_detail_edit = view.findViewById(R.id.btn_detail_edit);
        Button btn_detail_delete = view.findViewById(R.id.btn_detail_delete);
        RecyclerView rcv_class_instance_in_course = view.findViewById(R.id.rcv_class_instance_in_course);

        // Retrieve the yogaCourse from arguments
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            yogaCourse = (YogaCourse) bundleReceive.getSerializable("object_yoga_course");

            if (yogaCourse != null) {
                // Display course details
                txt_detail_course_name.setText("Course: " + yogaCourse.getCourseName());
                txt_detail_type_of_class.setText(yogaCourse.getTypeOfClass());
                txt_detail_day_of_week.setText("Start on: " + yogaCourse.getDayOfWeek());
                txt_detail_time.setText("Time: " + yogaCourse.getTimeOfCourse());
                txt_detail_capacity.setText("Capacity: " + yogaCourse.getCapacity() + " persons");
                txt_detail_duration.setText("Duration: " + yogaCourse.getDuration() + " minutes");
                txt_detail_price_per_class.setText("Price: $" + yogaCourse.getPricePerClass());
                txt_detail_description.setText(yogaCourse.getDescription());

                Toast.makeText(getContext(), "Price: "+yogaCourse.getPricePerClass()
                        +"Type: "+yogaCourse.getTypeOfClass(), Toast.LENGTH_SHORT).show();

                // Set image based on course type
                switch (yogaCourse.getTypeOfClass()) {
                    case "Flow Yoga":
                        img_decoration_detail_course.setImageResource(R.drawable.img_yoga_class_01);
                        break;
                    case "Aerial Yoga":
                        img_decoration_detail_course.setImageResource(R.drawable.img_yoga_class_02);
                        break;
                    case "Family Yoga":
                        img_decoration_detail_course.setImageResource(R.drawable.img_yoga_class_03);
                        break;
                    default:
                        img_decoration_detail_course.setImageResource(R.drawable.img_yoga_class_01);
                        break;
                }

                // Set edit and delete button actions
                btn_detail_edit.setOnClickListener(v -> {
                    homepageActivity.transferDataToFragmentPage(new EditYogaCourseFragment(), "object_yoga_course", yogaCourse);
                });

                btn_detail_delete.setOnClickListener(v -> {
                    showDeleteDialog(yogaCourse);
                });
            }
        }

        //Recycle view class instance
        int courseId = yogaCourse.getCourseId();
        ArrayList<ClassInstance> classInstanceList=dbHelper.getAllClassInstance();

        rcv_class_instance_in_course.setLayoutManager(new LinearLayoutManager(homepageActivity));
        classInstanceInClassAdapter = new ClassInstanceInClassAdapter(classInstanceList,yogaCourse,courseId, new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                homepageActivity.transferDataToFragmentPage(new DetailClassInstanceFragment(),"object_class_instance",object);
            }
        });
        rcv_class_instance_in_course.setAdapter(classInstanceInClassAdapter);


        return view;
    }


    private void showDeleteDialog(YogaCourse yogaCourse){
        AlertDialog.Builder builer=new AlertDialog.Builder(getContext());
        builer.setTitle("Delete a course");
        builer.setMessage("Are you sure you want to delete this course?");
        builer.setPositiveButton("Yes",((dialog, which) -> {
            YogaCourse yogaCourse_delete=yogaCourse;
            Toast.makeText(getContext(), ""+yogaCourse.getCourseName(), Toast.LENGTH_SHORT).show();
            boolean result=dbHelper.deleteYogaCourse(yogaCourse_delete);
            if(result){
                Toast.makeText(getContext(), "Deleted this course", Toast.LENGTH_SHORT).show();
                homepageActivity.replaceFragment(new CourseFragment());
                dialog.dismiss();
            }else{
                Toast.makeText(getContext(), "Delete error", Toast.LENGTH_SHORT).show();
            }

        }));

        builer.setNegativeButton("No",((dialog, which) -> {
            dialog.dismiss();
        }));
        builer.show();

    }
}