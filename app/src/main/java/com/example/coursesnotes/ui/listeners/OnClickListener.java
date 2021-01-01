package com.example.coursesnotes.ui.listeners;

import com.example.coursesnotes.models.Course;
import com.example.coursesnotes.models.Note;

public interface OnClickListener {

    public void OnClick(int id);

    public void OnDelete(Course course);

    public void OnUpdate(Course course);
}
