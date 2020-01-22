package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.R;
import com.google.gson.Gson;

public class popup_topic_2 extends AppCompatActivity {

    TextView tv_titel;
    TextView tv_tid;
    TextView tv_topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_topic_2);
        Button bigBtn = findViewById(R.id.btn_big);
        bigBtn.setText("Add \n topic");



        tv_titel = findViewById(R.id.pop_topic_titel);
        tv_tid = findViewById(R.id.pop_topic_tid);
        tv_topic = findViewById(R.id.pop_topic_description);




        bigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Topic topic = new Topic(tv_titel.getText().toString(), tv_topic.getText().toString(), (Integer.parseInt(tv_tid.getText().toString())*60));

                //LAVER TOPIC OM TIL ET JSON OBJEKT JEG KAN SENDE MED INTENTET
                Gson gson = new Gson();
                String myJson = gson.toJson(topic);

                Intent intent = new Intent();
                intent.putExtra("nytopic", myJson);
                setResult(RESULT_OK, intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }
}
