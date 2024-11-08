package com.example.finalyoga.ClassInstanceFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalyoga.Adapter.SQL.ClassInstanceAdapter;
import com.example.finalyoga.HomepageActivity;
import com.example.finalyoga.Interface.IClickItemListener;
import com.example.finalyoga.R;
import com.example.finalyoga.YogaCourseFragment.DetailYogaCourseFragment;
import com.example.finalyoga.database.DatabaseHelper;
import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.YogaCourse;

import java.util.ArrayList;


public class ClassInstanceFragment extends Fragment {


    private RecyclerView rvClassInstance;
    private ClassInstanceAdapter classInstanceAdapter;

    private HomepageActivity homepageActivity;

    private ArrayList<ClassInstance> classInstancesList;
    private ArrayList<YogaCourse> yogaCoursesList;

    private View view;

    private DatabaseHelper dbHelper;
    public ClassInstanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_class_instance, container, false);

        homepageActivity=(HomepageActivity) requireActivity();
        rvClassInstance=view.findViewById(R.id.rvClassInstance);
        Button btnAddClassInstance=view.findViewById(R.id.btnAddClassInstance);

        dbHelper=new DatabaseHelper(getContext());
        classInstancesList=dbHelper.getAllClassInstance();
        yogaCoursesList=dbHelper.getAllYogaCourse();
        rvClassInstance.setLayoutManager(new LinearLayoutManager(homepageActivity));
        classInstanceAdapter=new ClassInstanceAdapter(classInstancesList, yogaCoursesList, new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                homepageActivity.transferDataToFragmentPage(new DetailClassInstanceFragment(),"object_class_instance",object);
            }
        });

        rvClassInstance.setAdapter(classInstanceAdapter);

        btnAddClassInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homepageActivity.replaceFragment(new AddClassInstanceFragment());
            }
        });
        return view;
    }

}