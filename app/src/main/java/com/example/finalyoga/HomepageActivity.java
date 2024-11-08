package com.example.finalyoga;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.finalyoga.ClassInstanceFragment.ClassInstanceFragment;
import com.example.finalyoga.YogaCourseFragment.CourseFragment;
import com.example.finalyoga.database.DatabaseHelper;
import com.example.finalyoga.database.DatabaseSyncFirebase;
import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.User;
import com.example.finalyoga.database.model.YogaCourse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomepageActivity extends AppCompatActivity {

    ImageView imgHome, imgAdd,imgYogaClass, imgAccount;
    private DatabaseHelper dbHelper;
    private DatabaseSyncFirebase databaseSyncFirebase;
    private DatabaseReference databaseReference;
    private final String url="https://yogaapp-355e5-default-rtdb.asia-southeast1.firebasedatabase.app/";
    SwipeRefreshLayout swipeRefreshLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);

        imgHome=findViewById(R.id.imgHome);
        imgAdd=findViewById(R.id.imgAdd);
        imgYogaClass=findViewById(R.id.imgYogaClass);
        imgAccount=findViewById(R.id.imgAccount);

        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);

        dbHelper=new DatabaseHelper(HomepageActivity.this);
        databaseReference=FirebaseDatabase.getInstance(url).getReference();

        databaseSyncFirebase=new DatabaseSyncFirebase(dbHelper,databaseReference);

        databaseSyncFirebase.PushDataToFireBase();
        createLoadData();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_main, new CourseFragment())
                .commit();

        imgHome.setOnClickListener(v -> {
            replaceFragment(new CourseFragment());
        });

        imgYogaClass.setOnClickListener(v -> {
            replaceFragment(new ClassInstanceFragment());
        });
    }

    @SuppressLint("ResourceAsColor")
    private void createLoadData() {
        swipeRefreshLayout.setColorSchemeColors(R.color.DeepPurple700,R.color.lightBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                databaseSyncFirebase.PushDataToFireBase();
                Toast.makeText(HomepageActivity.this, "Sync successful data to Firebase", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void transferDataToFragmentPage(Fragment fragment, String key, Object object) {
        Bundle bundle = new Bundle();
        if(object instanceof YogaCourse) {
            bundle.putSerializable(key, (YogaCourse) object);
        }
        if(object instanceof User) {
            bundle.putSerializable(key, (User) object);
        }
        if(object instanceof ClassInstance) {
            bundle.putSerializable(key, (ClassInstance) object);
        }
        fragment.setArguments(bundle);
        replaceFragment(fragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragmentTransaction.commit();
    }


}