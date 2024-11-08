package com.example.finalyoga.ClassInstanceFragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyoga.HomepageActivity;
import com.example.finalyoga.R;
import com.example.finalyoga.YogaCourseFragment.CourseFragment;
import com.example.finalyoga.YogaCourseFragment.EditYogaCourseFragment;
import com.example.finalyoga.database.DatabaseHelper;
import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.YogaCourse;

import java.util.ArrayList;

public class DetailClassInstanceFragment extends Fragment {

    private TextView txt_detail_class_course_name,txt_detail_class_day_of_week,
            txt_detail_class_date,txt_detail_class_teacher_name,txt_detail_class_additional_comment;
    private Button btn_detail_class_delete, btn_detail_class_edit;
    private ImageView img_decoration_detail_course;

    private Bundle bundle;
    private ClassInstance classInstance;
    private DatabaseHelper dbHelper;
    private HomepageActivity homepageActivity;
    public DetailClassInstanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_class_instance, container, false);

        homepageActivity=(HomepageActivity) getActivity();
        dbHelper=new DatabaseHelper(getContext());

        txt_detail_class_course_name=view.findViewById(R.id.txt_detail_class_course_name);
        txt_detail_class_day_of_week=view.findViewById(R.id.txt_detail_class_day_of_week);
        txt_detail_class_date=view.findViewById(R.id.txt_detail_class_date);
        txt_detail_class_teacher_name=view.findViewById(R.id.txt_detail_class_teacher_name);
        txt_detail_class_additional_comment=view.findViewById(R.id.txt_detail_class_additional_comment);
        btn_detail_class_delete=view.findViewById(R.id.btn_detail_class_delete);
        btn_detail_class_edit=view.findViewById(R.id.btn_detail_class_edit);
        img_decoration_detail_course=view.findViewById(R.id.img_decoration_detail_course);

        bundle=getArguments();
        if (bundle != null) {
            classInstance = (ClassInstance) bundle.getSerializable("object_class_instance");

            if (classInstance != null) {
                // Display course details
                YogaCourse yogaCourse=getYogaCourseById(classInstance.getCourse_id());
                txt_detail_class_course_name.setText("Course: " + yogaCourse.getCourseName());
                txt_detail_class_day_of_week.setText("Day of week: "+yogaCourse.getDayOfWeek());
                txt_detail_class_date.setText("Date: " + classInstance.getDate_classInstance());
                txt_detail_class_teacher_name.setText("Teacher: " + classInstance.getTeacher_name());
                txt_detail_class_additional_comment.setText("Additonal comment: " + classInstance.getAdditional_comments());

                Toast.makeText(getContext(), ""+classInstance.getDate_classInstance(), Toast.LENGTH_SHORT).show();
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
                btn_detail_class_edit.setOnClickListener(v -> {
                    homepageActivity.transferDataToFragmentPage(new EditClassInstanceFragment(), "object_class_instance", classInstance);
                });

                btn_detail_class_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteDialog(classInstance);
                    }
                });
            }
        }
        return view;
    }

    private void showDeleteDialog(ClassInstance classInstance) {
        AlertDialog.Builder builer=new AlertDialog.Builder(getContext());
        builer.setTitle("Delete a class");
        builer.setMessage("Are you sure you want to delete this class?");
        builer.setPositiveButton("Yes",((dialog, which) -> {
            ClassInstance classInstance_delete=classInstance;
            Toast.makeText(getContext(), ""+classInstance.getClassInstacne_id(), Toast.LENGTH_SHORT).show();
            boolean result=dbHelper.deleteClassInstance(classInstance_delete);
            if(result){
                Toast.makeText(getContext(), "Deleted this class", Toast.LENGTH_SHORT).show();
                homepageActivity.replaceFragment(new ClassInstanceFragment());
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

    private YogaCourse getYogaCourseById(int courseId) {
        ArrayList<YogaCourse> yogaCourseList=dbHelper.getAllYogaCourse();
        for(YogaCourse yogaCourse: yogaCourseList){
            if(yogaCourse.getCourseId()==courseId){
                return yogaCourse;
            }
        }
        return null;
    }
}