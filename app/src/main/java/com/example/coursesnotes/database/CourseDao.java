package com.example.coursesnotes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursesnotes.models.Course;
import com.example.coursesnotes.models.Note;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM courses")
    List<Course> selectAllCourses();

    @Update
    void updateCourse(Course... course);

    @Insert
    void insertACourse(Course course);

    @Delete
    void deleteACourse(Course... course);
}
