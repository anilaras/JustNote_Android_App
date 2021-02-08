package com.aras.notepad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aras.notepad.Adapters.NoteAdapter;
import com.aras.notepad.abstracts.NoteDatabase;
import com.aras.notepad.data.Entities.Note;
import com.aras.notepad.tools.LocalDataManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteDatabase noteDB;
    private RecyclerView noteRecyclerView;
    private List<Note> notelist;
    private NoteAdapter noteAdapter;
    private FloatingActionButton fabAdd;
    private ConstraintLayout mainLayout;
    private Toolbar toolbar;
    LocalDataManager ldm;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.mainLayout);
        toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("JustNote");
        toolbar.setTitleTextColor(getColor(R.color.white));
        setSupportActionBar(toolbar);
        fabAdd = findViewById(R.id.FaButtonAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this, NoteAddActivity.class);
                //addIntent.putExtra();
                startActivityForResult(addIntent,2);
            }
        });

        noteDB = NoteDatabase.getDatabase(getApplicationContext());
        //Note not = new Note("Deneme Notu", "Lorem ipsum dolor sit ameth", LocalDate.now().toString());
        //noteDB.noteDao().setNote(not);
        notelist = noteDB.noteDao().getAllNotes();

        noteRecyclerView = findViewById(R.id.recyclerView);
        noteRecyclerView.setHasFixedSize(true);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteAdapter = new NoteAdapter(this, notelist, noteDB,mainLayout);

        noteRecyclerView.setAdapter(noteAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_Add:
                Intent intent = new Intent(this, NoteAddActivity.class);
                startActivityForResult(intent,2);
                break;
/*            case R.id.action_privateNote:
                Intent intentPrivate = new Intent(this, PasswordActivity.class);
                startActivity(intentPrivate);
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2){
            noteAdapter.updateList(noteDB.noteDao().getAllNotes());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.updateList(noteDB.noteDao().getAllNotes());
    }
}