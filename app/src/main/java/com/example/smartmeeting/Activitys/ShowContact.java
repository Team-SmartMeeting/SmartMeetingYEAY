package com.example.smartmeeting.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.smartmeeting.R;

/**
 * @author Søren Aarup Poulsen
 */


public class ShowContact extends Activity {

    EditText tv_name;
    EditText tv_phone;
    EditText tv_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contact);

        final Intent intent = getIntent();

        Button edit_contact = findViewById(R.id.edit_contact);
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

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.widthPixels;
//
//        getWindow().setLayout((int)(width*.8),(int)(height*.8));

    }
}
