package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartmeeting.R;

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

        Button editProfile = (Button) findViewById(R.id.btn_big);
        editProfile.setText("Save \n Changes");
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(startIntent);
                finish();
            }
        });


        //Menuen
        Button btn_profile = findViewById(R.id.btn_profile_menu);
        Button btn_meetings = findViewById(R.id.btn_meeting_menu);
        Button btn_groupe = findViewById(R.id.btn_groupes_menu);
        Button btn_contacts = findViewById(R.id.btn_contacts_menu);

        btn_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactList.class);
                startActivity(intent);
                finish();

            }
        });

        btn_groupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), groups_list.class);
                startActivity(intent);
                finish();

            }
        });

        btn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingOverview.class);
                startActivity(intent);
                finish();
            }
        });

        btn_profile.setBackgroundResource(R.drawable.button_pressed);


    }
}
