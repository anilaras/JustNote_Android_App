package com.aras.notepad.abstracts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.aras.notepad.data.Entities.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM Note WHERE Id = :NoteId")
    Note getNote(int NoteId);

    @Query("UPDATE NOTE SET title=:title, text=:text, noteTime= :time, colorHex= :color WHERE Id = :NoteId")
    void updateNote(int NoteId, String title, String text, String time, int color);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setNote(Note not);

    @Delete
    void delLog(Note not);

    @Query("DELETE FROM Note")
    void deleteAllNote();
}
