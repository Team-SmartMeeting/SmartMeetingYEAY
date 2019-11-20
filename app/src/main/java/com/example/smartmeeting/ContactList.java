package com.example.smartmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class ContactList extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        checkSharedPreferences();

    }

    private void checkSharedPreferences() {




    }
}
