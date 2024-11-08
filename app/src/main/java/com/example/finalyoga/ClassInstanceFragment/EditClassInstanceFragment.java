package com.example.finalyoga.ClassInstanceFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyoga.HomepageActivity;
import com.example.finalyoga.R;
import com.example.finalyoga.YogaCourseFragment.CourseFragment;
import com.example.finalyoga.database.DatabaseHelper;
import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.YogaCourse;

import java.util.ArrayList;
import java.util.Calendar;

public class EditClassInstanceFragment extends Fragment {

    private EditText edtDate_of_class, edtAdditional_comment;
    private TextView txt_class_course_day;
    private Spinner spinerCourseName,spinerTeacher_name;
    private ImageView imgCancelAddClass;
    private Button btnEditClassInstance;

    private DatabaseHelper dbHelper;
    private HomepageActivity homepageActivity;
    private ClassInstance classInstance;

    public EditClassInstanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_class_instance, container, false);

        edtDate_of_class=view.findViewById(R.id.edtDate_of_class);
        edtAdditional_comment=view.findViewById(R.id.edtAdditional_comment);
        txt_class_course_day=view.findViewById(R.id.txt_class_course_day);

        spinerCourseName=view.findViewById(R.id.spinerCourseName);
        spinerTeacher_name=view.findViewById(R.id.spinerTeacher_name);

        btnEditClassInstance=view.findViewById(R.id.btnAddClassInstance);
        imgCancelAddClass=view.findViewById(R.id.imgCancelAddClass);

        homepageActivity=(HomepageActivity) requireActivity();
        dbHelper=new DatabaseHelper(getContext());

        btnEditClassInstance.setText("Edit Class");

        showTeacherName();
        showCourseName();
        spinerCourseName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourseName = spinerCourseName.getSelectedItem().toString();
                YogaCourse selectedCourse = getYogaCourse(selectedCourseName);
                if (selectedCourse != null) {
                    txt_class_course_day.setText(selectedCourse.getDayOfWeek());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt_class_course_day.setText("Day of week: ");
            }
        });
        edtDate_of_class.setOnClickListener(v -> showDatePickerDialog(edtDate_of_class));
        if (getArguments() != null) {
            classInstance = (ClassInstance) getArguments().getSerializable("object_class_instance");
            if (classInstance != null) {
                YogaCourse yogaCourse=getYogaCourseById(classInstance.getCourse_id());
                edtDate_of_class.setText("Course: " + classInstance.getDate_classInstance());
                edtAdditional_comment.setText(classInstance.getAdditional_comments());
                txt_class_course_day.setText("Day of week: "+yogaCourse.getDayOfWeek());

                // Set dayOfWeek spinner to the course value
                int coursePosition = ((ArrayAdapter<String>) spinerCourseName.getAdapter()).getPosition(yogaCourse.getCourseName());
                spinerCourseName.setSelection(coursePosition);

                // Set typeOfClass spinner to the course value
                ArrayAdapter<CharSequence> teacherAdapter = (ArrayAdapter<CharSequence>) spinerTeacher_name.getAdapter();
                int teacherPosition = teacherAdapter.getPosition(classInstance.getTeacher_name());
                spinerTeacher_name.setSelection(teacherPosition);
            }
        }

        btnEditClassInstance.setOnClickListener(v -> {
            if(classInstance!=null){
                String class_course_name=spinerCourseName.getSelectedItem().toString();
                String date_classInstance=edtDate_of_class.getText().toString();
                String teacher_name= spinerTeacher_name.getSelectedItem().toString();
                String additional_comment= edtAdditional_comment.getText().toString();

                YogaCourse yogaCourse=getYogaCourse(class_course_name);

                ClassInstance updateClass=new ClassInstance(
                        classInstance.getClassInstacne_id(),
                        yogaCourse.getCourseId(),
                        date_classInstance,
                        teacher_name,
                        additional_comment
                );

                dbHelper=new DatabaseHelper(getContext());
                boolean result = dbHelper.editClassInstance(updateClass);

                if (result) {
                    Toast.makeText(getContext(), "Class instance updated successfully!", Toast.LENGTH_SHORT).show();
                    homepageActivity.replaceFragment(new ClassInstanceFragment());  // Navigate back to CourseFragment
                } else {
                    Toast.makeText(getContext(), "Failed to update class instance.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), "Error: Class Instance data not found.", Toast.LENGTH_SHORT).show();
            }

        }
        );

        imgCancelAddClass.setOnClickListener(v -> {
            DetailClassInstanceFragment detailFragment = new DetailClassInstanceFragment();
            Bundle args = new Bundle();
            args.putSerializable("object_class_instance", classInstance);
            detailFragment.setArguments(args);
            homepageActivity.replaceFragment(detailFragment);
        });
        return view;
    }


    public void showDatePickerDialog(EditText date_of_class) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Get the required day of the week from txt_class_course_day
        String requiredDay = txt_class_course_day.getText().toString().trim();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Set the selected date in the calendar instance
                    calendar.set(selectedYear, selectedMonth, selectedDay);
                    String selectDate = convertDate(selectedDay, selectedMonth + 1, selectedYear);
                    if(selectDate.equals(requiredDay)){
                        String selectedDate = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;

                        edtDate_of_class.setText(selectedDate);
                    }else {
                        // Show a message if the selected day doesn't match
                        Toast.makeText(getContext(), "Please select a date that falls on " + requiredDay, Toast.LENGTH_SHORT).show();
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private String convertDate(int day, int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Unknown";
        }
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

    private YogaCourse getYogaCourse(String classCourseName) {
        ArrayList<YogaCourse> yogaCourseList = dbHelper.getAllYogaCourse();
        for(YogaCourse yogaCourse: yogaCourseList) {
            if ((yogaCourse.getCourseName()).equalsIgnoreCase(classCourseName)) {
                return yogaCourse;
            }
        }
        return null;
    }

    private void showCourseName() {
        ArrayList<YogaCourse> yogaCourseList=dbHelper.getAllYogaCourse();
        ArrayList<String> coure_name_list=new ArrayList<>();
        if(!yogaCourseList.isEmpty()){
            for(YogaCourse yogaCourse: yogaCourseList){
                String coure_name=yogaCourse.getCourseName();
                coure_name_list.add(coure_name);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, coure_name_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerCourseName.setAdapter(adapter);
    }

    private void showTeacherName() {
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),
                R.array.teacher_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerTeacher_name.setAdapter(adapter);
    }
}