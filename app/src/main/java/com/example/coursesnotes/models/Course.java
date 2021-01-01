package com.example.coursesnotes.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "courses")
public class Course implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseName;
    private String courseDescription;

    public Course(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }



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

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    //-------------- parcelable functions --------------//
    protected Course(Parcel in) {
        courseId = in.readInt();
        courseName = in.readString();
        courseDescription = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(courseId);
        parcel.writeString(courseName);
        parcel.writeString(courseDescription);
    }
}
