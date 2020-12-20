package com.shetty.mvvmwithrecycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE="com.shetty.mvvmwithrecycleview.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="com.shetty.mvvmwithrecycleview.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY="com.shetty.mvvmwithrecycleview.EXTRA_PRIORITY";
    public static final String EXTRA_ID="com.shetty.mvvmwithrecycleview.EXTRA_ID";

    private EditText editTextTitle;
    private EditText editTextDesc;
    private NumberPicker numberPickerPriority;
    private AddEditNoteViewModel addEditNoteViewModel;
    private Intent intent;
    private int id=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editTextTitle=findViewById(R.id.edit_text_title);
        editTextDesc=findViewById(R.id.edit_text_description);
        numberPickerPriority=findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
        addEditNoteViewModel = new ViewModelProvider(this).get(AddEditNoteViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
         intent= getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDesc.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
            id=intent.getIntExtra(EXTRA_ID,-1);
        }
        else {
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title =editTextTitle.getText().toString();
        String description=editTextDesc.getText().toString();
        int priority=numberPickerPriority.getValue();
        Note note = new Note(title,description,priority);
        if(intent.hasExtra(EXTRA_ID)) {
            if(id==-1){
                Toast.makeText(AddEditNoteActivity.this,"Note Cannot be updated",Toast.LENGTH_LONG).show();
            }
            else {
                note.setId(id);
                addEditNoteViewModel.update(note);
                Toast.makeText(AddEditNoteActivity.this,"Note updated",Toast.LENGTH_LONG).show();
            }
        }
        else {
            addEditNoteViewModel.insert(note);
            Toast.makeText(AddEditNoteActivity.this,"Note added",Toast.LENGTH_LONG).show();
        }
            finish();
    }
}
