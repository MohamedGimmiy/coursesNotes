package com.example.coursesnotes.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.coursesnotes.R;
import com.example.coursesnotes.models.Note;
import com.example.coursesnotes.ui.listeners.OnClickListenerNote;

import java.util.List;

public class NoteAdapter extends  RecyclerView.Adapter<NoteAdapter.notesViewHolder> {

    private List<Note> notesList;
    private OnClickListenerNote onClickListenerNote;

    public void SetDataSource(List<Note>notes){
        this.notesList = notes;
        notifyDataSetChanged();
    }

    public void SetListener(OnClickListenerNote onClickListenerNote){
        this.onClickListenerNote = onClickListenerNote;
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noteitem, parent, false);
        return new notesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        final Note currentNote = notesList.get(position);
        holder.noteName.setText(currentNote.getName());
        holder.noteDescription.setText(currentNote.getDescription());

        holder.Edit.setOnClickListener(view -> {
            onClickListenerNote.OnUpdate(currentNote);
        });
        holder.Delete.setOnClickListener(view -> {
            Log.e("error", "is an error" + currentNote.getName());
            onClickListenerNote.OnDelete(currentNote);
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class notesViewHolder extends RecyclerView.ViewHolder {
        private TextView noteName, noteDescription;
        private Button Delete, Edit;
        public notesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName);
            noteDescription = itemView.findViewById(R.id.noteDescription);
            Delete = itemView.findViewById(R.id.DeleteNote);
            Edit = itemView.findViewById(R.id.EditNote);
        }
    }


}
