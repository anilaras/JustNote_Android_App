package com.aras.notepad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.aras.notepad.tools.LocalDataManager;
import com.aras.notepad.tools.md5;

public class SettingsActivity extends AppCompatActivity {
    LocalDataManager localDataManager;
    Button setPassword;
    EditText passWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        localDataManager = new LocalDataManager();
        passWord = findViewById(R.id.setPinText);
        setPassword = findViewById(R.id.buttonSetPin);

        setPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                localDataManager.getSharedPreference(this,"Password",md5.md5(passWord.getText().toString()));
            }
        });
    }
}