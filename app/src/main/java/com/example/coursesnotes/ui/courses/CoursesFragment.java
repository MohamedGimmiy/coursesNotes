package com.example.coursesnotes.ui.courses;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursesnotes.R;
import com.example.coursesnotes.adapters.CourseAdapter;
import com.example.coursesnotes.models.Course;
import com.example.coursesnotes.ui.listeners.OnClickListener;
import com.example.coursesnotes.viewModel.courseNotesViewModel;

import java.util.List;

public class CoursesFragment extends Fragment implements OnClickListener {

    private static courseNotesViewModel courseNotesViewModel;
    private int courseId = -1;
    private CourseAdapter courseAdapter;
    private Button addCourseButton;
    private RecyclerView recyclerView;

    public CoursesFragment(){}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //1. view model

        //2. inflate fragment_home
        View root = inflater.inflate(R.layout.activity_courses, container, false);
        recyclerView = root.findViewById(R.id.coursesList);
        addCourseButton = root.findViewById(R.id.AddCourse);
        //notesDatabase = NotesDatabase.getInstance(getContext());
        courseNotesViewModel = new ViewModelProvider(this)
                .get(courseNotesViewModel.class);
        LoadCourses();

        addCourseButton.setOnClickListener(view -> {
            // Navigating to add/edit note
            Bundle bundle = new Bundle();
            // True (add), False (edit)
            bundle.putBoolean("addEdit", true);
            Navigation.findNavController(view).navigate(R.id.addEditCourse, bundle);
        });
        return root;
    }
    private void LoadCourses() {
        courseNotesViewModel.getAllCourses().observe(getViewLifecycleOwner(),
                this::initRecycleView);
    }
    private void initRecycleView(List<Course> courses) {
        recyclerView.setHasFixedSize(true);
        courseAdapter = new CourseAdapter();
        courseAdapter.setDataSource(courses);
        courseAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(courseAdapter);
    }

    @Override
    public void OnClick(int id) {
        // setting a bundle to transfer data to another fragment
        CoursesFragment fragment = new CoursesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("courseId", id);
        fragment.setArguments(bundle);
        Navigation.findNavController(getView()).navigate(R.id.notesFragment, bundle);
    }

    @Override
    public void OnDelete(Course course) {
        //TODO delete a course
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to delete " + course.getCourseName()+ " ?").setTitle("Delete");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            // notesDatabase.noteDao().deleteANote(note);
            //new DeleteNote().execute(note);
            courseNotesViewModel.deleteCourse(course).observe(this, delete -> {
                if(delete){
                    LoadCourses();
                }
            });
        });
        AlertDialog dialog  = builder.create();
        dialog.show();
    }

    @Override
    public void OnUpdate(Course course) {
        //TODO delete a course
        Bundle bundle = new Bundle();
        bundle.putParcelable("course", course);
        bundle.putBoolean("addEdit", false);
        Navigation.findNavController(getView()).navigate(R.id.addEditCourse, bundle);
    }
}