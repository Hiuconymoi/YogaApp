package com.example.finalyoga.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.finalyoga.database.model.ClassInstance;
import com.example.finalyoga.database.model.User;
import com.example.finalyoga.database.model.YogaCourse;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME="YogaApp";
    protected static final int DATABASE_VERSION=2;

    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(YogaCourse.CREATE_TABLE);
        db.execSQL(ClassInstance.CREATE_TABLE);

        Log.d("DatabaseHelper", "Database created with tables.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + YogaCourse.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassInstance.TABLE_NAME);
        onCreate(db);
    }

    //USER

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.USERNAME + " = ? AND " + User.PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {username, password});

        boolean loginSuccess = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return loginSuccess;
    }

    public boolean addUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(User.USERNAME, username);
        values.put(User.PASSWORD, password);
        values.put(User.EMAIL, email);

        long result = db.insert(User.TABLE_NAME, null, values);

        db.close();

        return result != -1;
    }

    //YOGACOURSE

    public boolean addYogaCourse(YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(YogaCourse.COURSE_NAME, yogaCourse.getCourseName());
        values.put(YogaCourse.DAY_OF_WEEK, yogaCourse.getDayOfWeek());
        values.put(YogaCourse.TIME_OF_COURSE, yogaCourse.getTimeOfCourse());
        values.put(YogaCourse.CAPACITY, yogaCourse.getCapacity());
        values.put(YogaCourse.DURATION, yogaCourse.getDuration());
        values.put(YogaCourse.TYPE_OF_CLASS, yogaCourse.getTypeOfClass());
        values.put(YogaCourse.PRICE_PER_CLASS, yogaCourse.getPricePerClass());
        values.put(YogaCourse.DESCRIPTION, yogaCourse.getDescription());

        long result = db.insert(YogaCourse.TABLE_NAME, null, values);
        db.close();

        return result != -1; // Returns true if insert was successful
    }

    public boolean editYogaCourse(YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(YogaCourse.COURSE_NAME, yogaCourse.getCourseName());
        values.put(YogaCourse.DAY_OF_WEEK, yogaCourse.getDayOfWeek());
        values.put(YogaCourse.TIME_OF_COURSE, yogaCourse.getTimeOfCourse());
        values.put(YogaCourse.CAPACITY, yogaCourse.getCapacity());
        values.put(YogaCourse.DURATION, yogaCourse.getDuration());
        values.put(YogaCourse.TYPE_OF_CLASS, yogaCourse.getTypeOfClass());
        values.put(YogaCourse.PRICE_PER_CLASS, yogaCourse.getPricePerClass());
        values.put(YogaCourse.DESCRIPTION, yogaCourse.getDescription());

        int update = db.update(YogaCourse.TABLE_NAME, values, YogaCourse.COURSE_ID + " = ?", new String[]{String.valueOf(yogaCourse.getCourseId())});
        return update > 0;
    }

    public boolean deleteYogaCourse(YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(YogaCourse.TABLE_NAME, YogaCourse.COURSE_ID + " = ?", new String[]{String.valueOf(yogaCourse.getCourseId())});
        if(delete == 0) return false;
        db.close();
        return true;
    }

    public ArrayList<YogaCourse> getAllYogaCourse(){
        ArrayList<YogaCourse> courseList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cs=db.rawQuery("SELECT * FROM " + YogaCourse.TABLE_NAME, null);
        cs.moveToFirst();
        while(!cs.isAfterLast()){
            int yoga_course_id = cs.getInt(0);
            String course_name = cs.getString(1);
            String day_of_the_week = cs.getString(2);
            String time_of_course = cs.getString(3);
            int capacity = cs.getInt(4);
            int duration = cs.getInt(5);
            String type_of_course = cs.getString(6);
            double price_per_class = cs.getDouble(7);
            String description = cs.getString(8);
            YogaCourse yogaCourse = new YogaCourse(yoga_course_id, course_name, time_of_course,
                    day_of_the_week, capacity,price_per_class, duration, type_of_course, description);
            courseList.add(yogaCourse);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return courseList;
    }

    //CLASSINSTANCE

    public boolean addClassInstance(ClassInstance classInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(classInstance.COURSE_ID, classInstance.getCourse_id());
        values.put(classInstance.DATE, classInstance.getDate_classInstance());
        values.put(classInstance.TEACHER_NAME, classInstance.getTeacher_name());
        values.put(classInstance.ADDITIONAL_COMMENTS, classInstance.getAdditional_comments());


        long result = db.insert(ClassInstance.TABLE_NAME, null, values);
        db.close();

        return result != -1; // Returns true if insert was successful
    }

    public boolean editClassInstance(ClassInstance classInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(classInstance.COURSE_ID, classInstance.getCourse_id());
        values.put(classInstance.DATE, classInstance.getDate_classInstance());
        values.put(classInstance.TEACHER_NAME, classInstance.getTeacher_name());
        values.put(classInstance.ADDITIONAL_COMMENTS, classInstance.getAdditional_comments());

        int update = db.update(ClassInstance.TABLE_NAME, values, ClassInstance.INSTANCE_ID + " = ?", new String[]{String.valueOf(classInstance.getClassInstacne_id())});
        return update > 0;
    }


    public boolean deleteClassInstance(ClassInstance classInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(classInstance.TABLE_NAME, classInstance.INSTANCE_ID + " = ?", new String[]{String.valueOf(classInstance.getClassInstacne_id())});
        if(delete == 0) return false;
        db.close();
        return true;
    }

    public ArrayList<ClassInstance> getAllClassInstance(){
        ArrayList<ClassInstance> classInstanceList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cs= db.rawQuery("SELECT * FROM "+ ClassInstance.TABLE_NAME, null);
        cs.moveToFirst();
        while(!cs.isAfterLast()){
            int classInstacne_id=cs.getInt(0);
            int course_id=cs.getInt(1);
            String date_classInstance=cs.getString(2);
            String teacher_name=cs.getString(3);
            String additional_comments=cs.getString(4);

            ClassInstance classInstance=new ClassInstance(
                    classInstacne_id,
                    course_id,
                    date_classInstance,
                    teacher_name,
                    additional_comments);
            classInstanceList.add(classInstance);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return classInstanceList;
    }


}
