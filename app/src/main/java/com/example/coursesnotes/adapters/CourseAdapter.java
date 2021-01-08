package com.example.coursesnotes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursesnotes.R;
import com.example.coursesnotes.models.Course;
import com.example.coursesnotes.ui.listeners.OnClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.coursesViewHolder>{
    private List<Course> courseList;
    private OnClickListener onClickListener;

    public void setDataSource(List<Course> courses){
        this.courseList = courses;
        notifyDataSetChanged();
    }

    public void setListener(OnClickListener onClickListener){ this.onClickListener = onClickListener;}

    @NonNull
    @Override
    public coursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courseitem, parent, false);
        return new coursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull coursesViewHolder holder, int position) {
        final Course currentCourse = courseList.get(position);
        holder.bind(currentCourse, onClickListener);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class coursesViewHolder extends RecyclerView.ViewHolder{
        TextView courseName, courseDescription;
        FloatingActionButton DeleteCourse, EditCourse;
        public coursesViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            courseDescription = itemView.findViewById(R.id.courseDescription);
            DeleteCourse = itemView.findViewById(R.id.DeleteCourse);
            EditCourse = itemView.findViewById(R.id.EditCourse);
        }

        void bind(Course course, OnClickListener listener){
            courseName.setText(course.getCourseName());
            courseDescription.setText(course.getCourseDescription());
            itemView.setOnClickListener(view -> listener.OnClick(course.getCourseId()));

            DeleteCourse.setOnClickListener(view -> listener.OnDelete(course));

            EditCourse.setOnClickListener(view -> listener.OnUpdate(course));
        }
    }
}
