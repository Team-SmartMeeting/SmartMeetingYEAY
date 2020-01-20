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
        TextView myAwesomeTextView = (TextView)findViewById(R.id.btn_meeting_menu);
        myAwesomeTextView.setText("Meetings");



        tv_titel = findViewById(R.id.pop_topic_titel);
        tv_tid = findViewById(R.id.pop_topic_tid);
        tv_topic = findViewById(R.id.pop_topic_description);




        bigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Topic topic = new Topic(tv_titel.getText().toString(), tv_topic.getText().toString(), Integer.parseInt(tv_tid.getText().toString()));

                Gson gson = new Gson();
                String myJson = gson.toJson(topic);

                Intent intent = new Intent();
                intent.putExtra("nytopic", myJson);
                setResult(RESULT_OK, intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });

        btn_groupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), groups_list.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

            }
        });

        btn_meetings.setBackgroundResource(R.drawable.button_pressed);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }
}
