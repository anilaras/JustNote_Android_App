package com.aras.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.aras.notepad.abstracts.NoteDatabase;
import com.aras.notepad.data.Entities.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import top.defaults.colorpicker.ColorPickerPopup;

public class NoteEditActivity extends AppCompatActivity {
    NoteDatabase ndb;
    EditText Title, Text;
    private int mDefaultColor;
    Note editNote;
    private int noteId;
    private FloatingActionButton fabSaveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Title = findViewById(R.id.editTextEditNoteTitle);
        Text = findViewById(R.id.edittextEditNoteNote);
        Toolbar addToolBar = (Toolbar) findViewById(R.id.toolbarEdit);
        setSupportActionBar(addToolBar);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("Note", 0);
        Log.d("Note", String.valueOf(noteId));
        ndb = NoteDatabase.getDatabase(getApplicationContext());
        editNote = ndb.noteDao().getNote(noteId);
        Title.setText(editNote.title);
        Text.setText(editNote.text);
        mDefaultColor = editNote.colorHex;
        fabSaveEdit = findViewById(R.id.FaButtonEditSave);
        fabSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNoteSave();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delEdit:
                ndb.noteDao().delLog(ndb.noteDao().getNote(noteId));
                finish();
                return true;
            case R.id.pickColorEditMenu:
                View v = findViewById(R.id.pickColorEditMenu);
                new ColorPickerPopup.Builder(NoteEditActivity.this).initialColor(
                        editNote.colorHex) // set initial color
                        // of the color
                        // picker dialog
                        .enableBrightness(
                                true) // enable color brightness
                        // slider or not
                        .enableAlpha(
                                true) // enable color alpha
                        // changer on slider or
                        // not
                        .okTitle(
                                "Choose") // this is top right
                        // Choose button
                        .cancelTitle(
                                "Cancel") // this is top left
                        // Cancel button which
                        // closes the
                        .showIndicator(
                                true) // this is the small box
                        // which shows the chosen
                        // color by user at the
                        // bottom of the cancel
                        // button
                        .showValue(
                                true) // this is the value which
                        // shows the selected
                        // color hex code
                        // the above all values can be made
                        // false to disable them on the
                        // color picker dialog.
                        .build()
                        .show(
                                v,
                                new ColorPickerPopup.ColorPickerObserver() {
                                    @Override
                                    public void
                                    onColorPicked(int color) {
                                        // set the color
                                        // which is returned
                                        // by the color
                                        // picker
                                        mDefaultColor = color;

                                        // now as soon as
                                        // the dialog closes
                                        // set the preview
                                        // box to returned
                                        // color
                                    }
                                });
                return true;
            case R.id.saveEdit:
                updateNoteSave();
                return true;

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateNoteSave(){
        String gun = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String ay = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String yil = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        ndb.noteDao().updateNote(editNote.Id, Title.getText().toString(),Text.getText().toString(),gun + "." + ay + "." + yil,mDefaultColor);
        finish();
    }
}