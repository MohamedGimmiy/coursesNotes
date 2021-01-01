package com.example.coursesnotes.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "notes", foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "courseId",
        childColumns = "courseId",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int noteId;
    @ColumnInfo(name = "noteName")
    private String name;
    @ColumnInfo(name = "noteDescription")
    private String description;
    private int courseId;

    public Note(String name, String description, int courseId){
        this.name = name;
        this.description = description;
        this.courseId = courseId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getCourseId() {
        return courseId;
    }

    //--------------- Parcelable methods -------------------//
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(noteId);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(courseId);
    }
    public Note(Parcel parcel){

        noteId = parcel.readInt();
        name = parcel.readString();
        description = parcel.readString();
        courseId = parcel.readInt();
    }
}