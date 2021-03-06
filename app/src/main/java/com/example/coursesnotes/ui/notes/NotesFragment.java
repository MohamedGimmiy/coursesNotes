package com.example.coursesnotes.ui.notes;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursesnotes.R;
import com.example.coursesnotes.adapters.NoteAdapter;
import com.example.coursesnotes.models.Note;
import com.example.coursesnotes.ui.listeners.OnClickListenerNote;
import com.example.coursesnotes.viewModel.courseNotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesFragment extends Fragment implements OnClickListenerNote {

    private courseNotesViewModel courseNotesViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton AddNote;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        recyclerView = root.findViewById(R.id.notesList);
        AddNote = root.findViewById(R.id.AddNote);

        // Initializing our viewModel
                courseNotesViewModel = new ViewModelProvider(this)
                .get(courseNotesViewModel.class);

        LoadAllNotes();
        AddNote.setOnClickListener(view -> Navigation.findNavController(requireView()).navigate(R.id.addNoteGlobal));

        return root;
    }

    private void LoadAllNotes() {
        courseNotesViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> recycleViewSetup(notes));
    }

    private void recycleViewSetup(List<Note> notes) {
        recyclerView.setHasFixedSize(true);
        noteAdapter = new NoteAdapter();
        noteAdapter.SetListener(this);
        noteAdapter.SetDataSource(notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void OnDelete(Note note) {
        //TODO first Fire a dialog for confirmation (DONE)
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to delete " + note.getName()+ " ?").setTitle("Delete");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            // notesDatabase.noteDao().deleteANote(note);
            //new DeleteNote().execute(note);
            courseNotesViewModel.deleteNote(note).observe(this, delete -> {
                if(delete){
                    LoadAllNotes();
                }
            });
        });
        AlertDialog dialog  = builder.create();
        dialog.show();
    }

    @Override
    public void OnUpdate(Note note) {
        //TODO Navigate to Add/Edit fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("note", note);
        bundle.putBoolean("addEdit", false);
        Navigation.findNavController(requireView()).navigate(R.id.addEditNote, bundle);

    }
}