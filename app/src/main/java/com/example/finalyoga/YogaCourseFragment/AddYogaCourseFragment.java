package com.example.finalyoga.YogaCourseFragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalyoga.ClassInstanceFragment.ClassInstanceFragment;
import com.example.finalyoga.HomepageActivity;
import com.example.finalyoga.R;
import com.example.finalyoga.database.DatabaseHelper;
import com.example.finalyoga.database.model.YogaCourse;


public class AddYogaCourseFragment extends Fragment {

    private EditText editTextCourseName, editTextTime, editTextCapacity, editTextDuration, editTextPrice, editTextDescription;
    private Button btnAddCourse, btnBack;
    private Spinner spinerDayOfWeek,spinerType;
    private DatabaseHelper dbHelper;
    private HomepageActivity homepageActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_yoga_course, container, false);

        editTextCourseName = view.findViewById(R.id.editTextCourseName);
        editTextTime = view.findViewById(R.id.editTextTime);
        editTextCapacity = view.findViewById(R.id.editTextCapacity);
        editTextDuration = view.findViewById(R.id.editTextDuration);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextDescription = view.findViewById(R.id.editTextDescription);

        spinerType = view.findViewById(R.id.spinerType);
        spinerDayOfWeek = view.findViewById(R.id.spinerDayOfWeek);

        btnAddCourse = view.findViewById(R.id.btnAddCourse);
        btnBack = view.findViewById(R.id.btnBack);

        homepageActivity=(HomepageActivity) requireActivity();

        dbHelper = new DatabaseHelper(getActivity());

        showDayOfWeek();
        showTypeOfClass();

        btnAddCourse.setOnClickListener(v -> showConfirmationDialog());
        btnBack.setOnClickListener(v -> {
            homepageActivity.replaceFragment(new CourseFragment());
        });

        return view;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Course Addition")
                .setMessage("Do you want to add this course?")
                .setPositiveButton("Yes", (dialog, which) -> addCourseToDatabase())
                .setNegativeButton("No", null)
                .show();
    }

    private void addCourseToDatabase() {
        String courseName = editTextCourseName.getText().toString();
        String time = editTextTime.getText().toString();
        int capacity = Integer.parseInt(editTextCapacity.getText().toString());
        int duration = Integer.parseInt(editTextDuration.getText().toString());
        String type = spinerType.getSelectedItem().toString();
        String dayOfWeek = spinerDayOfWeek.getSelectedItem().toString();
        double price = Double.parseDouble(editTextPrice.getText().toString());
        String description = editTextDescription.getText().toString();

        YogaCourse yogaCourse = new YogaCourse(
                -1,
                courseName,
                time,
                dayOfWeek,
                capacity,
                price,
                duration,
                type,
                description);
        if(dbHelper.addYogaCourse(yogaCourse)){
            Toast.makeText(getContext(), "Add new Course successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Add new Course error", Toast.LENGTH_SHORT).show();
        }

        homepageActivity.replaceFragment(new CourseFragment());
    }

    public void showDayOfWeek(){
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),
                R.array.day_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerDayOfWeek.setAdapter(adapter);
    }
    public void showTypeOfClass(){
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),
                R.array.type_of_class, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerType.setAdapter(adapter);
    }
}
