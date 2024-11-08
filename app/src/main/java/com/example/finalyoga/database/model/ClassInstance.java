package com.example.finalyoga.database.model;

import java.io.Serializable;

public class ClassInstance implements Serializable {

    public static final String TABLE_NAME = "ClassInstance";

    // Column names
    public static final String INSTANCE_ID = "instance_id";
    public static final String COURSE_ID = "course_id";
    public static final String DATE = "date";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String ADDITIONAL_COMMENTS = "additional_comments";


    private int classInstacne_id;
    private int course_id;
    private String date_classInstance;
    private String teacher_name;
    private String additional_comments;

    public ClassInstance(int classInstacne_id, int course_id, String date_classInstance, String teacher_name, String additional_comments) {
        this.classInstacne_id = classInstacne_id;
        this.course_id = course_id;
        this.date_classInstance = date_classInstance;
        this.teacher_name = teacher_name;
        this.additional_comments = additional_comments;
    }

    public int getClassInstacne_id() {
        return classInstacne_id;
    }

    public void setClassInstacne_id(int classInstacne_id) {
        this.classInstacne_id = classInstacne_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getDate_classInstance() {
        return date_classInstance;
    }

    public void setDate_classInstance(String date_classInstance) {
        this.date_classInstance = date_classInstance;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getAdditional_comments() {
        return additional_comments;
    }

    public void setAdditional_comments(String additional_comments) {
        this.additional_comments = additional_comments;
    }

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + INSTANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COURSE_ID + " INTEGER NOT NULL, "
            + DATE + " TEXT NOT NULL, "
            + TEACHER_NAME + " INTEGER, "
            + ADDITIONAL_COMMENTS + " TEXT, "
            + "FOREIGN KEY(" + COURSE_ID + ") REFERENCES " + YogaCourse.TABLE_NAME + "(" + YogaCourse.COURSE_ID + ")"
            +")";
}
