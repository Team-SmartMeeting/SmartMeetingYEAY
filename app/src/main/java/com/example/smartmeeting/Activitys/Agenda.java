package com.example.smartmeeting.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Agenda extends AppCompatActivity {

    ArrayList<String> agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agenda = new ArrayList<>();

        //henter mødet med Gson
        Gson gson = new Gson();
        MeetingDTO myMeeting = gson.fromJson(getIntent().getStringExtra("meeting"), MeetingDTO.class);





        //KNAPPERNE
        Button btnPaticipants = findViewById(R.id.btn_add_participant);
        Button btnTopics = findViewById(R.id.btn_add_topic);
        btnPaticipants.setText("Add\n Participants");

//        btn_topics.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_topic = new Intent(getApplicationContext(),addtopic.class);
//                startActivity(intent_topic);
//            }
//        });

//        btn_paticipants.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_participants = new Intent(getApplicationContext(), participants.class);
//                startActivity(intent_participants);
//            }
//        });

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

    @Override
    protected void onResume() {
        super.onResume();

    }
}




