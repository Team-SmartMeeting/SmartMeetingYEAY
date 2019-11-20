package com.example.smartmeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class add_contact extends AppCompatActivity {

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

        Button btn = findViewById(R.id.btn_add_contact);

        // fortæller hvad knappen gør:
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amountOfContact = mPreferences.getInt("nr_of_contact", 0);

                String name = tv_name.getText().toString();
                String email = tv_email.getText().toString();
                String phoneNumber = tv_PhoneNumber.getText().toString();

                if (name != null && email.contains("@") && email.contains(".") && phoneNumber != null ){

                    String contact_String = name + "|" + email + "|" + phoneNumber;

                    mEditor.putString(Integer.toString(amountOfContact), contact_String);
                    mEditor.putInt("nr_of_contact", (amountOfContact+1));
                    mEditor.commit();
                    Toast.makeText(add_contact.this, "User saved",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
