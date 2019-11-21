package com.example.smartmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditProfile extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();


        TextView textName = (TextView) findViewById(R.id.name);
        TextView textPhone = (TextView) findViewById(R.id.phonenumber);
        TextView textEmail = (TextView) findViewById(R.id.email);
        TextView textCompany = (TextView) findViewById(R.id.company);
        TextView textAddress = (TextView) findViewById(R.id.address);
        TextView textZipCode = (TextView) findViewById(R.id.zip_code);
        TextView textCountry = (TextView) findViewById(R.id.country);

        /*
        textName.setHint();
        textPhone.setHint();
        textEmail.setHint();
        textCompany.setHint();
        textAddress.setHint();
        textZipCode.setHint();
        textCountry.setHint();
        */

        Button editProfile = (Button) findViewById(R.id.btn_save_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(startIntent);
            }
        });
    }
}
