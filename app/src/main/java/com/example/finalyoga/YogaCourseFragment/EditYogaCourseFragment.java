package com.example.finalyoga.YogaCourseFragment;

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

import com.example.finalyoga.ClassInstanceFragment.DetailClassInstanceFragment;
import com.example.finalyoga.HomepageActivity;
import com.example.finalyoga.R;
import com.example.finalyoga.database.DatabaseHelper;
import com.example.finalyoga.database.model.YogaCourse;

public class EditYogaCourseFragment extends Fragment {

    private HomepageActivity homepageActivity;
    private YogaCourse yogaCourse;

    EditText editTextCourseName;
    Spinner spinerDayOfWeek;
    EditText editTextTime;
    EditText editTextCapacity;
    EditText editTextDuration;
    Spinner spinerType;
    EditText editTextPrice;
    EditText editTextDescription;
    Button btnEditCourse;
    Button btnBack;

    public EditYogaCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_yoga_course, container, false);

        editTextCourseName = view.findViewById(R.id.editTextCourseName);
        spinerDayOfWeek = view.findViewById(R.id.spinerDayOfWeek);
        editTextTime = view.findViewById(R.id.editTextTime);
        editTextCapacity = view.findViewById(R.id.editTextCapacity);
        editTextDuration = view.findViewById(R.id.editTextDuration);
        spinerType = view.findViewById(R.id.spinerType);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        btnEditCourse = view.findViewById(R.id.btnAddCourse);  // Reusing the button
        btnBack=view.findViewById(R.id.btnBack);
        homepageActivity=(HomepageActivity) requireActivity();
        // Change button text to "Edit Course"
        btnEditCourse.setText("Edit Course");

        showDayOfWeek();
        showTypeOfClass();

        // Retrieve and display existing course data
        if (getArguments() != null) {
            yogaCourse = (YogaCourse) getArguments().getSerializable("object_yoga_course");
            if (yogaCourse != null) {
                editTextCourseName.setText(yogaCourse.getCourseName());
                editTextTime.setText(yogaCourse.getTimeOfCourse());
                editTextCapacity.setText(String.valueOf(yogaCourse.getCapacity()));
                editTextDuration.setText(String.valueOf(yogaCourse.getDuration()));
                editTextPrice.setText(String.valueOf(yogaCourse.getPricePerClass()));
                editTextDescription.setText(yogaCourse.getDescription());

                // Set dayOfWeek spinner to the course value
                ArrayAdapter<CharSequence> dayAdapter = (ArrayAdapter<CharSequence>) spinerDayOfWeek.getAdapter();
                int dayPosition = dayAdapter.getPosition(yogaCourse.getDayOfWeek());
                spinerDayOfWeek.setSelection(dayPosition);

                // Set typeOfClass spinner to the course value
                ArrayAdapter<CharSequence> typeAdapter = (ArrayAdapter<CharSequence>) spinerType.getAdapter();
                int typePosition = typeAdapter.getPosition(yogaCourse.getTypeOfClass());
                spinerType.setSelection(typePosition);
            }
        }

        // Handle edit button click
        btnEditCourse.setOnClickListener(v -> {
            if (yogaCourse != null) {  // Check if yogaCourse is not null
                // Retrieve updated values
                String courseName = editTextCourseName.getText().toString();
                String dayOfWeek = spinerDayOfWeek.getSelectedItem().toString();
                String timeOfCourse = editTextTime.getText().toString();
                int capacity = Integer.parseInt(editTextCapacity.getText().toString());
                int duration = Integer.parseInt(editTextDuration.getText().toString());
                String typeOfClass = spinerType.getSelectedItem().toString();
                double pricePerClass = Double.parseDouble(editTextPrice.getText().toString());
                String description = editTextDescription.getText().toString();

                // Update YogaCourse object with new data
                YogaCourse updatedCourse = new YogaCourse(
                        yogaCourse.getCourseId(),
                        courseName,
                        timeOfCourse,
                        dayOfWeek,
                        capacity,
                        pricePerClass,
                        duration,
                        typeOfClass,
                        description
                );

                // Update course in database
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                boolean result = dbHelper.editYogaCourse(updatedCourse);

                if (result) {
                    Toast.makeText(getContext(), "Course updated successfully!", Toast.LENGTH_SHORT).show();
                    homepageActivity.replaceFragment(new CourseFragment());  // Navigate back to CourseFragment
                } else {
                    Toast.makeText(getContext(), "Failed to update course.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Error: Course data not found.", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(v -> {
            DetailYogaCourseFragment detailFragment = new DetailYogaCourseFragment();
            Bundle args = new Bundle();
            args.putSerializable("object_yoga_course", yogaCourse);
            detailFragment.setArguments(args);
            homepageActivity.replaceFragment(detailFragment);
        });
        return view;
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