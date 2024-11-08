package com.example.finalyoga.ClassInstanceFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddClassInstanceFragment extends Fragment {

    private EditText edtDate_of_class, edtAdditional_comment;
    private TextView txt_class_course_day;
    private Spinner spinerCourseName,spinerTeacher_name;
    private ImageView imgCancelAddClass;
    private Button btnAddClassInstance;

    private DatabaseHelper dbHelper;
    private HomepageActivity homepageActivity;

    public AddClassInstanceFragment() {}

    @SuppressLint("MissingInflatedId")
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

        btnAddClassInstance=view.findViewById(R.id.btnAddClassInstance);
        imgCancelAddClass=view.findViewById(R.id.imgCancelAddClass);

        homepageActivity=(HomepageActivity) requireActivity();
        dbHelper=new DatabaseHelper(getContext());

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
        btnAddClassInstance.setOnClickListener(v -> showConfirmationDialog());
        imgCancelAddClass.setOnClickListener(v -> {
            homepageActivity.replaceFragment(new ClassInstanceFragment());
        });
        return view;
    }


    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Course Addition")
                .setMessage("Do you want to add this course?")
                .setPositiveButton("Yes", (dialog, which) -> addClassInstanceDB())
                .setNegativeButton("No", null)
                .show();
    }

    private void addClassInstanceDB() {
        String class_course_name=spinerCourseName.getSelectedItem().toString();
        String date_classInstance=edtDate_of_class.getText().toString();
        String teacher_name= spinerTeacher_name.getSelectedItem().toString();
        String additional_comment= edtAdditional_comment.getText().toString();

        YogaCourse yogaCourse=getYogaCourse(class_course_name);

        ClassInstance classInstance=new ClassInstance(
                -1,
                yogaCourse.getCourseId(),
                date_classInstance,
                teacher_name,
                additional_comment
        );
        if(dbHelper.addClassInstance(classInstance)){
            Toast.makeText(getContext(), "Add new Class Instance successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Add new Class Instance error", Toast.LENGTH_SHORT).show();
        }
        homepageActivity.replaceFragment(new ClassInstanceFragment());
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