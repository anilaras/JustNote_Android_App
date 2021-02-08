package com.aras.notepad.abstracts;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aras.notepad.data.Entities.Note;

@Database(entities = {Note.class}, version = 3)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static volatile NoteDatabase noteDatabase;

    public static NoteDatabase getDatabase(final Context context){
        if (noteDatabase == null){
            synchronized (NoteDatabase.class){
                if (noteDatabase == null){
                    noteDatabase = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"KayitOrnek").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return noteDatabase;
    }

}
