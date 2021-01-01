package com.example.coursesnotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coursesnotes.R;
import com.example.coursesnotes.models.Course;

import java.util.ArrayList;

public class coursesListAdapter extends ArrayAdapter<Course> {
    public coursesListAdapter(@NonNull Context context, ArrayList<Course> courseArrayList) {
        super(context,0, courseArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_row, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.spinnerChoose);
        Course currentItem = getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getCourseName());
        }
        return convertView;
    }
}
