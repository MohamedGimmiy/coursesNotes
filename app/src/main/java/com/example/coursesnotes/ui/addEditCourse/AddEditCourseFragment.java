package com.example.coursesnotes.ui.addEditCourse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.coursesnotes.R;
import com.example.coursesnotes.models.Course;
import com.example.coursesnotes.viewModel.courseNotesViewModel;


public class AddEditCourseFragment extends Fragment {

    //
    private Button AddEdit;
    private Button Cancel;
    private EditText courseName;
    private EditText courseDescription;
    private courseNotesViewModel courseNotesViewModel;

    private Boolean isAddEdit;
    private Course course;
    public AddEditCourseFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //To be completed if an edited course is sent
            isAddEdit =  getArguments().getBoolean("addEdit");
            course = getArguments().getParcelable("course");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_course, container, false);

        courseNotesViewModel = new ViewModelProvider(this)
                .get(courseNotesViewModel.class);

        courseName = view.findViewById(R.id.CourseNameField);
        courseDescription = view.findViewById(R.id.CourseDescriptionField);
        AddEdit = view.findViewById(R.id.AddCourseButton);
        Cancel = view.findViewById(R.id.CancelCourseButton);


        if(isAddEdit){
            AddEdit.setOnClickListener(view1 -> {
                if (isNotValid()) return;
                courseNotesViewModel.addCourse(
                        courseName.getText().toString(),
                        courseDescription.getText().toString()
                ).observe( getViewLifecycleOwner(), added -> {
                    if(added){
                        Navigation.findNavController(view).popBackStack();
                    }
                });
            });
        }
        else {
            // Edit
            courseName.setText(course.getCourseName());
            courseDescription.setText(course.getCourseDescription());
            AddEdit.setText("Edit");
            AddEdit.setOnClickListener(view1 -> {
                if (isNotValid()) return;
                course.setCourseName(courseName.getText().toString());
                course.setCourseDescription(courseDescription.getText().toString());
                courseNotesViewModel.updateCourse(course).observe(getViewLifecycleOwner(), updated -> {
                    if(updated){
                        Navigation.findNavController(view).popBackStack();
                    }
                });
            });
        }


        Cancel.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });

        return view;
    }

    private boolean isNotValid() {
        if(courseName.getText().toString().isEmpty()){
            courseName.setError("Please enter Course name");
            return true;
        } else if(courseDescription.getText().toString().isEmpty()){
            courseDescription.setError("Please enter description");
            return true;
        }
        return false;
    }
}