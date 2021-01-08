package com.example.coursesnotes.ui.addEditNote;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursesnotes.MainActivity;
import com.example.coursesnotes.R;
import com.example.coursesnotes.models.Note;
import com.example.coursesnotes.viewModel.courseNotesViewModel;

public class AddEditNoteFragment extends Fragment {

    private static Boolean isAddEdit;
    private static int courseId;
    private Note note;
    private courseNotesViewModel courseNotesViewModel;

    private Button AddEdit;
    private Button Cancel;
    private EditText noteName;
    private EditText noteDescription;

    public AddEditNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isAddEdit =  getArguments().getBoolean("addEdit");
            courseId = getArguments().getInt("courseId");
            note = getArguments().getParcelable("note");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =  inflater.inflate(R.layout.fragment_add_edit_note, container, false);
        // Initializing our viewModel
        courseNotesViewModel = new ViewModelProvider(this)
                .get(courseNotesViewModel.class);


        AddEdit = view.findViewById(R.id.AddNoteButton);
        Cancel = view.findViewById(R.id.CancelNoteButton);
        noteName = view.findViewById(R.id.NoteNameField);
        noteDescription = view.findViewById(R.id.NoteDescriptionField);

         //TODO adding/editing a note

        //TODO 1.adding a note
        if(isAddEdit){
            // Add
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("Add Note");
            AddEdit.setOnClickListener(view1 -> {

                //Validation
                if (isNotValid()) return;

                courseNotesViewModel.addANote(new Note(
                        noteName.getText().toString(),
                        noteDescription.getText().toString(),
                        courseId
                )).observe(getViewLifecycleOwner(), added -> {
                    if(added){
                        // navigate back
                        Navigation.findNavController(view).popBackStack();
                    }
                });
            });

        } else {
            // Edit
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(note.getName());
            noteName.setText(note.getName());
            noteDescription.setText(note.getDescription());
            AddEdit.setText("Edit");
            AddEdit.setOnClickListener(view1 -> {
                if (isNotValid()) return;
                note.setName(noteName.getText().toString());
                note.setDescription(noteDescription.getText().toString());
                courseNotesViewModel.updateNote(note).observe(getViewLifecycleOwner(), updated -> {
                    if(updated){
                        Navigation.findNavController(view).popBackStack();
                    }
                });
            });
        }

        Cancel.setOnClickListener(view1 -> Navigation.findNavController(view).popBackStack());

        return view;
    }

    private boolean isNotValid() {
        if(noteName.getText().toString().isEmpty()){
            noteName.setError("Please enter name");
            return true;
        } else if(noteDescription.getText().toString().isEmpty()){
            noteDescription.setError("Please enter description");
            return true;
        }
        return false;
    }
}