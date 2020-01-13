package com.example.smartmeeting.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterAgenda;
import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterContactlist;
import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Agenda extends AppCompatActivity {

    ArrayList<Topic> agenda;
    Gson gson;

    ArrayList<String> TopicTitels;
    ArrayList<String> TopicTime;
    ArrayList<String> TopicDescription;


    //
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agenda = new ArrayList<>();

        //henter mødet med Gson
        gson = new Gson();
        MeetingDTO myMeeting = gson.fromJson(getIntent().getStringExtra("meeting"), MeetingDTO.class);


        TopicTitels = new ArrayList<>();
        TopicTime = new ArrayList<>();
        TopicDescription = new ArrayList<>();
        listView = findViewById(R.id.listview_agenda);

        //KNAPPERNE
        Button btnPaticipants = findViewById(R.id.btn_big);
        Button btnTopics = findViewById(R.id.btn_add_topic);
        btnPaticipants.setText("Add\n Participants");

        btnTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Agenda.this, popup_topic_2.class);
                startActivityForResult(intent, 1);

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
                Topic nytTopic = gson.fromJson(data.getStringExtra("nytopic"), Topic.class);
                agenda.add(nytTopic);

                UpdateList();
            }

        }
    }

    public void UpdateList(){

        //CLEAR ALLE ARRAYLISTER SÅ DE ER KLAR TIL AT BLIVE REPOSTET
        TopicTitels.clear();
        TopicTime.clear();
        TopicDescription.clear();

        //TILFØJER ALLE TOPICS TIL ARRAYLISTER
        for (int i = 0; i < agenda.size(); i++) {
            TopicTitels.add(agenda.get(i).getTopicName());
            TopicTime.add(Integer.toString(agenda.get(i).getTopicDuration()));
            TopicDescription.add(agenda.get(i).getDescription());
        }

        // DER SKAL LAVES EN NY ADAPTER TIL AT SMIDE DATAEN IND I LISTEN
        listView.setAdapter(new CustomAdapterAgenda(Agenda.this,TopicTitels, TopicTime, TopicDescription));


//        listView.setClickable(true);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Agenda.this, ShowContact.class);
//                intent.putExtra("name",agenda.get(position).getTopicName());
//                intent.putExtra("email",agenda.get(position).getTopicDuration());
//                intent.putExtra("number",agenda.get(position).getDescription());
//                startActivity(intent);
//
//            }
//        });




    }
}




