package com.example.coursesnotes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.coursesnotes.models.Course;
import com.example.coursesnotes.models.Note;


@Database(entities = {Note.class, Course.class},version = 1, exportSchema = false)
public abstract class NotesDatabase  extends RoomDatabase {

    private static NotesDatabase instance;

    public abstract NoteDao noteDao();
    public abstract CourseDao courseDao();

    public static NotesDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context, NotesDatabase.class, "notes-database.dp")
                    .build();
        }
         return  instance;
    }
}
