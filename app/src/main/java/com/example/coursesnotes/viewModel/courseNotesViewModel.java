package com.example.coursesnotes.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coursesnotes.database.NotesDatabase;
import com.example.coursesnotes.models.Course;
import com.example.coursesnotes.models.Note;

import java.util.List;

public class courseNotesViewModel extends AndroidViewModel {
    private static NotesDatabase notesDatabase;

    //1. Courses operations
    private static MutableLiveData<List<Course>> coursesLiveData = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isCourseAdded = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isCourseUpdated = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isCourseDeleted = new MutableLiveData<>();

    //TODO UPDATE AND DELETE
    //2. Notes operations
    private static Note currentNote;
    private static MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isNoteAdded = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isNoteUpdated = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isNoteDeleted = new MutableLiveData<>();


    public courseNotesViewModel(@NonNull Application application) {
        super(application);
        notesDatabase = NotesDatabase.getInstance(application.getApplicationContext());
    }


    //1. Database CRUD operation execution

    //1.1 Courses operations
    public LiveData<List<Course>> getAllCourses(){
        new SelectAllCourses().execute();
        return coursesLiveData;
    }


    public LiveData<Boolean> addCourse(String courseName, String courseDescription){
        new addACourse().execute(courseName, courseDescription);
        return isCourseAdded;
    }

    public LiveData<Boolean>updateCourse(Course course){
        new UpdateCourse().execute(course);
        return isCourseUpdated;
    }

    public LiveData<Boolean>deleteCourse(Course course){
        new DeleteCourse().execute(course);
        return isCourseDeleted;
    }


    //1.2 Notes operations
    public LiveData<List<Note>> getAllNotes(){
        new SelectAllNotes().execute();
        return notesLiveData;
    }
    public LiveData<List<Note>> getAllNotesByCourse(int courseId){
         new SelectAllNotesByCourseId().execute(courseId);
        return notesLiveData;
    }
    public LiveData<Boolean> addANote(Note note){
        new InsertNote().execute(note);
        return isNoteAdded;
    }
    public LiveData<Boolean>updateNote(Note note){
        currentNote = note;
        new UpdateNote().execute(currentNote);
        return isNoteUpdated;
    }
    public LiveData<Boolean>deleteNote(Note note){
        new DeleteNote().execute(note);
        return isNoteDeleted;
    }

    //2. Database CRUD operations in background thread

    //2.1 Courses Database operations


    private class SelectAllCourses extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            // Getting all courses from database
            coursesLiveData.postValue(notesDatabase.courseDao().selectAllCourses());
            return null;
        }
    }


    private static  class addACourse extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isCourseAdded.setValue(false);
        }

        @Override
        protected Void doInBackground(String... strings) {
            Course course = new Course( strings[0], strings[1]);
            notesDatabase.courseDao().insertACourse(course);
            isCourseAdded.postValue(true);
            return null;
        }

    }


    private static class UpdateCourse extends AsyncTask<Course, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isCourseUpdated.setValue(false);
        }

        @Override
        protected Void doInBackground(Course... courses) {
            notesDatabase.courseDao().updateCourse(courses[0]);
            isCourseUpdated.postValue(true);
            return null;
        }

    }

    private class DeleteCourse extends AsyncTask<Course, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isCourseDeleted.setValue(false);
        }

        @Override
        protected Void doInBackground(Course... courses) {
            notesDatabase.courseDao().deleteACourse(courses[0]);
            isCourseDeleted.postValue(true);
            return null;
        }
    }

    //2.2 Notes Database operations
    private static class SelectAllNotes extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... Void) {
            notesLiveData.postValue(notesDatabase.noteDao().loadAllNotes());
            return null;
        }
    }

    private static class SelectAllNotesByCourseId extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            notesLiveData.postValue(notesDatabase.noteDao().loadNotesByCourseId(integers[0]));
            return null;
        }
    }

    private static class UpdateNote extends AsyncTask<Note, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isNoteUpdated.setValue(false);
        }

        @Override
        protected Void doInBackground(Note... note) {
            notesDatabase.noteDao().updateANote(note[0]);
            isNoteUpdated.postValue(true);
            return null;
        }

    }

    private class InsertNote extends AsyncTask<Note, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isNoteAdded.setValue(false);
        }

        @Override
        protected Void doInBackground(Note... note) {
            notesDatabase.noteDao().insertANote(note[0]);
            isNoteAdded.postValue(true);
            return null;
        }
    }

    private class DeleteNote extends AsyncTask<Note, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isNoteDeleted.setValue(false);
        }

        @Override
        protected Void doInBackground(Note... note) {
            notesDatabase.noteDao().deleteANote(note[0]);
            isNoteDeleted.postValue(true);
            return null;
        }
    }
}
