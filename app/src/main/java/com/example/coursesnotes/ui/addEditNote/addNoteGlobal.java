package com.example.coursesnotes.ui.addEditNote;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coursesnotes.R;
import com.example.coursesnotes.adapters.coursesListAdapter;
import com.example.coursesnotes.models.Course;
import com.example.coursesnotes.models.Note;
import com.example.coursesnotes.viewModel.courseNotesViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class addNoteGlobal extends Fragment implements AdapterView.OnItemSelectedListener {


    private Button Add;
    private Button Cancel;
    private EditText NoteName;
    private EditText NoteDescription;
    private courseNotesViewModel courseNotesViewModel;
    private Spinner spinner;
    private coursesListAdapter courseListAdapter;
    private ArrayList<Course> courses;
    private Course selected;
    public addNoteGlobal() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_note_global, container, false);


        NoteName = view.findViewById(R.id.NoteNameField);
        NoteDescription = view.findViewById(R.id.NoteDescriptionField);
        spinner = view.findViewById(R.id.spinner);
        Add = view.findViewById(R.id.AddNoteButton);
        Cancel = view.findViewById(R.id.CancelNoteButton);

        spinner.setOnItemSelectedListener(this);

        courseNotesViewModel = new ViewModelProvider(this)
                .get(courseNotesViewModel.class);

        LoadCoursesSpinner();

        Add.setOnClickListener(view1 -> {
            if(isNotValid()) return;
            courseNotesViewModel.addANote(new Note(
                    NoteName.getText().toString(),
                    NoteDescription.getText().toString(),
                    selected.getCourseId()
            )).observe(getViewLifecycleOwner(), added -> {
                Navigation.findNavController(view).popBackStack();
            });
        });

        Cancel.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });
        return view;
    }

    private void LoadCoursesSpinner() {
        courseNotesViewModel.getAllCourses().observe(getViewLifecycleOwner(), mycourses -> {
            courses = (ArrayList<Course>) mycourses;
            //TODO question two solutions database solution or adapter solution???
            courseListAdapter = new coursesListAdapter(getActivity(), courses);
            spinner.setAdapter(courseListAdapter);
        });
    }

    private boolean isNotValid() {
        if(NoteName.getText().toString().isEmpty()){
            NoteName.setError("Please enter Note name");
            return true;
        } else if(NoteDescription.getText().toString().isEmpty()){
            NoteDescription.setError("Please enter Note description");
            return true;
        } else if(selected == null) {
            Toast.makeText(getContext(), "Please select a course if exists", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO tomorrow
         selected = (Course) adapterView.getItemAtPosition(i);
        Toast.makeText(getActivity(), selected.getCourseName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //TODO tomorrow
    }
}