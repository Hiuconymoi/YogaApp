package com.example.finalyoga.database;

import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.YogaCourse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DatabaseSyncFirebase {
    DatabaseHelper dbHelper;
    DatabaseReference databaseReference;

    public DatabaseSyncFirebase(DatabaseHelper dbHelper, DatabaseReference databaseReference){
        this.dbHelper=dbHelper;
        this.databaseReference=databaseReference;
    }

    public void PushDataToFireBase(){
        databaseReference.removeValue();

        ArrayList<YogaCourse> yogaCourseList=dbHelper.getAllYogaCourse();
        ArrayList<ClassInstance> classInstanceList=dbHelper.getAllClassInstance();

        for(YogaCourse yogaCourse:yogaCourseList){
            databaseReference.child(YogaCourse.TABLE_NAME).child(String.valueOf(yogaCourse.getCourseId())).setValue(yogaCourse);
        }
        for(ClassInstance classInstance:classInstanceList){
            databaseReference.child(ClassInstance.TABLE_NAME).child(String.valueOf(classInstance.getClassInstacne_id())).setValue(classInstance);
        }
    }

}
