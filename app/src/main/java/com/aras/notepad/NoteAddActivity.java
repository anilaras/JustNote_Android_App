package com.aras.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.aras.notepad.abstracts.NoteDatabase;
import com.aras.notepad.data.Entities.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import top.defaults.colorpicker.ColorPickerPopup;

public class NoteAddActivity extends AppCompatActivity {
    private EditText title;
    private EditText text;
    private NoteDatabase ndb;
    private FloatingActionButton fabAddSave;
    private Note note;
    // this is the default color of the preview box
    private int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        title = findViewById(R.id.editTextEditNoteTitle);
        text = findViewById(R.id.edittextEditNoteNote);
        ndb = NoteDatabase.getDatabase(getApplicationContext());
        mDefaultColor = getResources().getColor(R.color.primary);
        Toolbar addToolBar = (Toolbar) findViewById(R.id.toolbarAdd);
        setSupportActionBar(addToolBar);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title.setTypeface(Typeface.MONOSPACE);
        text.setTypeface(Typeface.MONOSPACE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Typeface typeface = ResourcesCompat.getFont(this, R.font.playlistscript );
        //int titleId = getResources().getIdentifier("action_bar_title", "id","android");
        //TextView yourTextView = (TextView) findViewById(titleId);
        //yourTextView.setTextColor(getResources().getColor(R.color.white));
        //yourTextView.setTypeface(typeface);
        fabAddSave = findViewById(R.id.FaButtonAddAdd);
        fabAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pickColorMenu:
                View w = findViewById(R.id.addConstLay);
                new ColorPickerPopup.Builder(NoteAddActivity.this).initialColor(
                        Color.RED) // set initial color
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
                                w,
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
                                        //buttonColor.setBackgroundColor(mDefaultColor);
                                    }
                                });
                return true;
            case R.id.saveAdd:
                saveNote();
                return true;
            // Respond to the action bar's Up/Home/back button
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveNote(){
        String gun = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String ay = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String yil = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        note = new Note();
        note.title = title.getText().toString();
        note.text = text.getText().toString();
        note.noteTime = gun + "." + ay + "." + yil;
        note.colorHex = mDefaultColor;
        ndb.noteDao().setNote(note);
        setResult(2);
        finish();
    }
}
