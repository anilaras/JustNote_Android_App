package com.aras.notepad.data.Entities;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.aras.notepad.R;

@Entity(tableName = "Note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int Id;

    public String title;

    public String text;

    public String noteTime;

    public boolean isPrivate;

    public int pin;

    public int colorHex;

    public Note(){

    }

    public Note(String title, String text, String noteTime, boolean isprivate, int colorHex){
        this.title = title;
        this.text = text;
        this.noteTime = noteTime;
        this.isPrivate = isprivate;
        this.colorHex =colorHex;
    }
    public Note(String title, String text, String noteTime, boolean isprivate){
        this.title = title;
        this.text = text;
        this.noteTime = noteTime;
        this.isPrivate = isprivate;
        this.colorHex = R.color.material_on_background_disabled;

    }
}
