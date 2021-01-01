package com.example.coursesnotes.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursesnotes.models.Note;
import com.example.coursesnotes.models.Course;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes")
    List<Note> loadAllNotes();

    @Query("SELECT * FROM notes WHERE courseId=:id")
    List<Note> loadNotesByCourseId(final int id);

    @Query("SELECT * FROM notes WHERE noteId =:id ")
    Note SelectNoteById(int id);

    @Update
    void updateANote(Note notes);

    @Insert
    void insertANote(Note note);

    @Delete
    void deleteANote(Note notes);
}
