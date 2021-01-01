package com.example.coursesnotes.ui.coursesNotes;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.coursesnotes.R;
import com.example.coursesnotes.adapters.NoteAdapter;
import com.example.coursesnotes.models.Note;
import com.example.coursesnotes.ui.listeners.OnClickListenerNote;
import com.example.coursesnotes.viewModel.courseNotesViewModel;

import java.util.List;


public class coursesNotesFragment extends Fragment implements OnClickListenerNote {

    // TODO: Customize parameters
    private int courseId = -1;
    private courseNotesViewModel courseNotesViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private Button addEditNote;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public coursesNotesFragment() {
    }

    // TODO: Customize parameter initialization

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //TODO receive course id
            courseId = getArguments().getInt("courseId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notes, container, false);


        recyclerView = view.findViewById(R.id.notesList);

        // Initializing our viewModel
        courseNotesViewModel = new ViewModelProvider(this)
                .get(courseNotesViewModel.class);

        loadNotes();
        // TODO adding a note
        addEditNote = view.findViewById(R.id.AddNote);

        addEditNote.setOnClickListener(view1 -> {
            // Navigating to add/edit note
            Bundle bundle = new Bundle();
            // True (add), False (edit)
            bundle.putBoolean("addEdit", true);
            bundle.putInt("courseId", courseId);
            Navigation.findNavController(view).navigate(R.id.addEditNote, bundle);
        });


        return view;
    }

    private void loadNotes() {
        courseNotesViewModel.getAllNotesByCourse(courseId).observe(getViewLifecycleOwner(),
                notes -> {
            recycleViewSetup(notes);
        });
    }

    private void recycleViewSetup(List<Note> notes) {
        recyclerView.setHasFixedSize(true);
        noteAdapter = new NoteAdapter();
        noteAdapter.SetDataSource(notes);
        noteAdapter.SetListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public void OnDelete(Note note) {
        //TODO delete immediately
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to delete " + note.getName()+ " ?").setTitle("Delete");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            // notesDatabase.noteDao().deleteANote(note);
            //new DeleteNote().execute(note);
            courseNotesViewModel.deleteNote(note).observe(this, delete -> {
                if(delete){
                    loadNotes();
                }
            });
        });
        AlertDialog dialog  = builder.create();
        dialog.show();
    }

    @Override
    public void OnUpdate(Note note) {
        //TODO update a note (navigate to Add/Edit note fragment)
        // send a boolean (true add), (false edit)

        Bundle bundle = new Bundle();
        bundle.putParcelable("note", note);
        bundle.putBoolean("addEdit", false);
        Navigation.findNavController(getView()).navigate(R.id.addEditNote, bundle);
    }
}