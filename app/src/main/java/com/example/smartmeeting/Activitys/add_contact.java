package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeeting.R;

public class add_contact extends AppCompatActivity {

    /**
     * @author Søren Aarup Poulsen
     */


    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        //Skaber objekterne som er i XML filen.
        final TextView tv_name = findViewById(R.id.create_contact_name_et);
        final TextView tv_email = findViewById(R.id.create_contact_email_et);
        final TextView tv_PhoneNumber = findViewById(R.id.create_contact_phonenumber_et);

        Button btn = findViewById(R.id.btn_big);
        btn.setText("Add\n Contact");

        // fortæller hvad knappen gør:
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amountOfContact = mPreferences.getInt("nrofcontact", 0);

                String name = tv_name.getText().toString();
                String email = tv_email.getText().toString();
                String phoneNumber = tv_PhoneNumber.getText().toString();

                if (name != null && email.contains("@") && email.contains(".") && phoneNumber != null ){

                    String contact_String = name + "     " + email + "     " + phoneNumber + "     " + Integer.toString(amountOfContact);
                    String nr = Integer.toString(amountOfContact);

                    mEditor.putString(nr, contact_String);
                    mEditor.commit();
                    amountOfContact++;
                    mEditor.putInt("nrofcontact", (amountOfContact));
                    mEditor.commit();
                    Toast.makeText(add_contact.this, "User saved",Toast.LENGTH_LONG).show();

                    finish();
                }
            }
        });

        //Menuen
        Button btn_profile = findViewById(R.id.btn_profile_menu);
        Button btn_meetings = findViewById(R.id.btn_meeting_menu);
        Button btn_groupe = findViewById(R.id.btn_groupes_menu);
        Button btn_contacts = findViewById(R.id.btn_contacts_menu);


        btn_contacts.setBackgroundResource(R.drawable.button_pressed);

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

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
