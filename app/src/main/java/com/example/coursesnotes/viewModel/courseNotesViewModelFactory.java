package com.example.coursesnotes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class courseNotesViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public courseNotesViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new courseNotesViewModel(application);
    }
}
