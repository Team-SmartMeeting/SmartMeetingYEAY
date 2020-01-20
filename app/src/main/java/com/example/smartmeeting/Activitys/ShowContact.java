package com.example.smartmeeting.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.smartmeeting.R;

/**
 * @author Søren Aarup Poulsen & Simon Philipsen
 */


public class ShowContact extends Activity {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    EditText tv_name;
    EditText tv_phone;
    EditText tv_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contact);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        final Intent intent = getIntent();

        Button edit_contact = findViewById(R.id.edit_contact);
        Button save_contact = findViewById(R.id.save_contact);

        tv_name = findViewById(R.id.text_name);
        tv_phone = findViewById(R.id.text_phonenumber);
        tv_email = findViewById(R.id.text_email);

        tv_name.setText(intent.getStringExtra("name"));
        tv_phone.setText(intent.getStringExtra("number"));
        tv_email.setText(intent.getStringExtra("email"));

        tv_name.setEnabled(false);
        tv_phone.setEnabled(false);
        tv_email.setEnabled(false);

        edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_name.setEnabled(true);
                tv_phone.setEnabled(true);
                tv_email.setEnabled(true);
            }
        });
        save_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amountOfContact = mPreferences.getInt("nrofcontact", 0);

                String name = tv_name.getText().toString();
                String email = tv_email.getText().toString();
                String phoneNumber = tv_phone.getText().toString();

                if (name != null && email.contains("@") && email.contains(".") && phoneNumber != null ){

                    String contact_String = name + "     " + email + "     " + phoneNumber + "     " + (amountOfContact);
                    String nr = Integer.toString(amountOfContact);

                    mEditor.putString(Integer.toString(intent.getIntExtra("posistion",0)),contact_String);
                    mEditor.commit();
                    amountOfContact++;
                    mEditor.putInt("nrofcontact", (amountOfContact));
                    mEditor.commit();
                    Toast.makeText(ShowContact.this, "Contact saved",Toast.LENGTH_LONG).show();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }else {
                    Toast.makeText(ShowContact.this, "Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.widthPixels;
//
//        getWindow().setLayout((int)(width*.8),(int)(height*.8));

    }
}
