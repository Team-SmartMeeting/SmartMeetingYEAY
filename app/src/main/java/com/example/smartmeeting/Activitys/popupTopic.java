package com.example.smartmeeting.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.smartmeeting.R;

/**
 * @author Søren Aarup Poulsen
 */


public class popupTopic extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_topic);

        Intent intent = getIntent();


        TextView tv_name = findViewById(R.id.text_name);
        TextView tv_phone = findViewById(R.id.text_phonenumber);
        TextView tv_email = findViewById(R.id.text_email);

        tv_name.setText(intent.getStringExtra("name"));
        tv_phone.setText(intent.getStringExtra("number"));
        tv_email.setText(intent.getStringExtra("email"));



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

    }
}
