package com.aras.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.PrimaryKey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aras.notepad.tools.LocalDataManager;
import com.aras.notepad.tools.md5;

public class PasswordActivity extends AppCompatActivity {
    EditText password;
    Button buttonOk;
    LocalDataManager ldm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        buttonOk = findViewById(R.id.buttonOkPassword);
        password = findViewById(R.id.editTextNumberPassword);
        try {
            String passCheck = ldm.getSharedPreference(this,"Password","NULL");
            if (passCheck.equals("NULL") || passCheck.isEmpty()){
                Intent settingsIntent = new Intent(PasswordActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                finish();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            String Password = ldm.getSharedPreference(getApplicationContext(),"Password","NULL");
            @Override
            public void onClick(View v) {
                if ( md5.md5(password.getText().toString()).equals(Password)){
                    Intent passWordIntent = new Intent(PasswordActivity.this, PrivateNoteActivity.class);
                    startActivity(passWordIntent);
                }else {
                    finish();
                }
            }
        });
    }
}