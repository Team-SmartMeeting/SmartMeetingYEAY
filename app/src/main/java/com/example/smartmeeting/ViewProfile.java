package com.example.smartmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewProfile extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        TextView textName = findViewById(R.id.name);
        TextView textPhone = findViewById(R.id.phonenumber);
        TextView textEmail = findViewById(R.id.email);
        TextView textCompany = findViewById(R.id.company);
        TextView textAddress = findViewById(R.id.address);
        TextView textZipCode = findViewById(R.id.zip_code);
        TextView textCountry = findViewById(R.id.country);
        /*
        textName.setText();
        textPhone.setText();
        textEmail.setText();
        textCompany.setText();
        textAddress.setText();
        textZipCode.setText();
        textCountry.setText();
        */
        Button editProfile = findViewById(R.id.btn_edit_profile);
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startIntent = new Intent(getApplicationContext(), EditProfile.class);
                    startActivity(startIntent);
                }
            });


    }
}
