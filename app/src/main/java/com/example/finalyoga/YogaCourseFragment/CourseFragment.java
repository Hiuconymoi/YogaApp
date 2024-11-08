package com.example.finalyoga.YogaCourseFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalyoga.Adapter.SQL.CourseAdapter;
import com.example.finalyoga.HomepageActivity;
import com.example.finalyoga.Interface.IClickItemListener;
import com.example.finalyoga.R;
import com.example.finalyoga.database.DatabaseHelper;
import com.example.finalyoga.database.model.YogaCourse;

import java.util.ArrayList;

public class CourseFragment extends Fragment {

    private RecyclerView rvYogaCourse;
    private CourseAdapter courseAdapter;

    //Define activity
    private HomepageActivity homepageActivity;

    private ArrayList<YogaCourse> courseList;
    private DatabaseHelper dbHelper;

    public CourseFragment(){}
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        rvYogaCourse = view.findViewById(R.id.rvYogaCourse);
        homepageActivity=(HomepageActivity) requireActivity();
        dbHelper= new DatabaseHelper(getContext());

        Button btnAdd=view.findViewById(R.id.btnAdd);
        courseList=dbHelper.getAllYogaCourse();

        rvYogaCourse.setLayoutManager(new LinearLayoutManager(homepageActivity));
        courseAdapter = new CourseAdapter(courseList, new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                homepageActivity.transferDataToFragmentPage(new DetailYogaCourseFragment(),"object_yoga_course",object);
            }
        });

        rvYogaCourse.setAdapter(courseAdapter);

        btnAdd.setOnClickListener(v -> {
            homepageActivity.replaceFragment(new AddYogaCourseFragment());
        });

        return view;
    }

}
