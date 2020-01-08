package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.smartmeeting.R;

public class popup_topic_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_topic_2);


        Intent intent = getIntent();


        TextView tv_titel = findViewById(R.id.pop_topic_titel);
        TextView tv_tid = findViewById(R.id.pop_topic_tid);
        TextView tv_topic = findViewById(R.id.pop_topic_description);






        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));
    }
}
