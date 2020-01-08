package com.example.smartmeeting.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Agenda extends AppCompatActivity {

    ArrayList<Topic> agenda;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agenda = new ArrayList<>();

        //henter mødet med Gson
        gson = new Gson();
        MeetingDTO myMeeting = gson.fromJson(getIntent().getStringExtra("meeting"), MeetingDTO.class);







        //KNAPPERNE
        Button btnPaticipants = findViewById(R.id.btn_big);
        Button btnTopics = findViewById(R.id.btn_add_topic);
        btnPaticipants.setText("Add\n Participants");

        btnTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Agenda.this, popup_topic_2.class);
                startActivity(intent);

            }
        });

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

    //taget fra stackoverflow
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                Topic nytTopic = gson.fromJson(data.getStringExtra("edittextvalue"), Topic.class);
                agenda.add(nytTopic);

            }

        }
    }

    public void UpdateList(){

        ArrayList<String> TopicTitels = new ArrayList<>();
        ArrayList<String> TopicTime = new ArrayList<>();
        ArrayList<String> TopicDescription = new ArrayList<>();


    }
}




