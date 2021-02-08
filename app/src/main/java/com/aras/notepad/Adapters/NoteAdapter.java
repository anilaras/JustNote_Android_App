package com.aras.notepad.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.telecom.Call;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aras.notepad.NoteEditActivity;
import com.aras.notepad.R;
import com.aras.notepad.abstracts.NoteDatabase;
import com.aras.notepad.data.Entities.Note;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Context mContext;
    private List<Note> noteList;
    private NoteDatabase noteDB;
    private ConstraintLayout mainL;

    public NoteAdapter(Context mContext, List<Note> noteList, NoteDatabase noteDB, ConstraintLayout mainL){
        this.mContext = mContext;
        this.noteList = noteList;
        this.noteDB = noteDB;
        this.mainL = mainL;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView text;
        public TextView date;
        public CardView cardNote;
        public Button delButton;
        public Button editButton;

        public  NoteViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.titleView);
            text = view.findViewById(R.id.noteView);
            date = view.findViewById(R.id.dateView);
            cardNote = view.findViewById(R.id.noteCard);
            delButton = view.findViewById(R.id.buttonDelete);
            title.setTypeface(Typeface.MONOSPACE);
            text.setTypeface(Typeface.MONOSPACE);
            date.setTypeface(Typeface.MONOSPACE);
            delButton.setTypeface(Typeface.MONOSPACE);
/*
            title.setTypeface(ResourcesCompat.getFont(mContext, R.font.playlistscript));
            text.setTypeface(ResourcesCompat.getFont(mContext, R.font.playlistscript));
            date.setTypeface(ResourcesCompat.getFont(mContext, R.font.playlistscript));
            delButton.setTypeface(ResourcesCompat.getFont(mContext, R.font.playlistscript)); */
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.noteview, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        String title = noteList.get(position).title;
        String text = noteList.get(position).text;
        String time = noteList.get(position).noteTime;
        holder.cardNote.setCardBackgroundColor(noteList.get(position).colorHex);
        holder.title.setText(title);
        holder.text.setText(text);
        holder.date.setText(time);

        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDB.noteDao().delLog(noteList.get(position));
                Snackbar snackbar = Snackbar.make(mainL, R.string.notedeleted, Snackbar.LENGTH_LONG);
                snackbar.show();
                noteList = noteDB.noteDao().getAllNotes();
                notifyDataSetChanged();
            }
        });

        holder.cardNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(mContext, NoteEditActivity.class);
                intent.putExtra("Note",noteList.get(position).Id);
                mContext.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void updateList(List<Note> newlist) {
        noteList.clear();
        noteList.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
