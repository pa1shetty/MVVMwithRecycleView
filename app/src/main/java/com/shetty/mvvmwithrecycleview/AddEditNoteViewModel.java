package com.shetty.mvvmwithrecycleview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AddEditNoteViewModel extends AndroidViewModel {
    private final NoteRepository repository;
    public AddEditNoteViewModel(@NonNull Application application) {
        super(application);
        repository =new NoteRepository(application);
    }
    public void insert(Note note){
        repository.insert(note);
    }
    public void update(Note note){
        repository.update(note);

    }
}
