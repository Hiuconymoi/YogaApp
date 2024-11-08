package com.example.finalyoga.database.model;

import java.io.Serializable;

public class YogaCourse implements Serializable {

        public static final String TABLE_NAME = "YogaCourse";

        // Column names
        public static final String COURSE_ID = "course_id";
        public static final String COURSE_NAME="course_name";
        public static final String DAY_OF_WEEK = "day_of_week";
        public static final String TIME_OF_COURSE = "time_of_course";
        public static final String CAPACITY = "capacity";
        public static final String DURATION = "duration";
        public static final String PRICE_PER_CLASS = "price_per_class";
        public static final String TYPE_OF_CLASS = "type_of_class";
        public static final String DESCRIPTION = "description";

        private int courseId;
        private String courseName;
        private String dayOfWeek;
        private String timeOfCourse;
        private int capacity;
        private int duration;
        private double pricePerClass;
        private String typeOfClass;
        private String description;

        public YogaCourse(int courseId, String courseName, String timeOfCourse, String dayOfWeek, int capacity, double pricePerClass, int duration, String typeOfClass, String description) {
            this.courseId = courseId;
            this.courseName = courseName;
            this.timeOfCourse = timeOfCourse;
            this.dayOfWeek = dayOfWeek;
            this.capacity = capacity;
            this.pricePerClass = pricePerClass;
            this.duration = duration;
            this.typeOfClass = typeOfClass;
            this.description = description;
        }

    // Getter and Setter methods
        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getDayOfWeek() {
                return dayOfWeek;
            }

        public void setDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public String getTimeOfCourse() {
            return timeOfCourse;
        }

        public void setTimeOfCourse(String timeOfCourse) {
            this.timeOfCourse = timeOfCourse;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public double getPricePerClass() {
            return pricePerClass;
        }

        public void setPricePerClass(double pricePerClass) {
            this.pricePerClass = pricePerClass;
        }

        public String getTypeOfClass() {
            return typeOfClass;
        }

        public void setTypeOfClass(String typeOfClass) {
            this.typeOfClass = typeOfClass;
        }

    public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        // Optionally, override toString() for easy debugging
        @Override
        public String toString() {
            return "YogaCourse{" +
                    "courseId=" + courseId +
                    ", dayOfWeek='" + dayOfWeek + '\'' +
                    ", timeOfCourse='" + timeOfCourse + '\'' +
                    ", capacity=" + capacity +
                    ", duration=" + duration +
                    ", pricePerClass=" + pricePerClass +
                    ", typeId=" + typeOfClass +
                    ", description='" + description + '\'' +
                    '}';
        }

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COURSE_NAME + " TEXT NOT NULL, "
                + DAY_OF_WEEK + " TEXT NOT NULL, "
                + TIME_OF_COURSE + " TEXT NOT NULL, "
                + CAPACITY + " INTEGER NOT NULL, "
                + DURATION + " INTEGER NOT NULL, "
                + PRICE_PER_CLASS + " REAL NOT NULL, "
                + TYPE_OF_CLASS + " TEXT NOT NULL, "
                + DESCRIPTION + " TEXT)";

}
