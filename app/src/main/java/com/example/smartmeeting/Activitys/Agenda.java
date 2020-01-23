package com.example.smartmeeting.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterAgenda;
import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterContactlist;
import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Agenda extends AppCompatActivity {

    ArrayList<Topic> agenda;
    Gson gson;

    ArrayList<String> TopicTitels;
    ArrayList<String> TopicTime;
    ArrayList<String> TopicDescription;


    DatabaseReference ref;
    FirebaseDatabase database;

    MeetingDTO myMeeting;


    //
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agenda = new ArrayList<>();

        //Firebase
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Meetings");

        //SÆTTER OVERSKRIFT
        TextView tv = findViewById(R.id.metting_name);


        TopicTitels = new ArrayList<>();
        TopicTime = new ArrayList<>();
        TopicDescription = new ArrayList<>();
        listView = findViewById(R.id.listview_agenda);
        //KNAPPERNE
        Button btnPaticipants = findViewById(R.id.btn_big);
        Button btnTopics = findViewById(R.id.btn_add_topic);
        btnPaticipants.setText("Add\n Participants");

        //henter mødet med Gson
        gson = new Gson();
        if (getIntent().hasExtra("meeting")) {
            myMeeting = gson.fromJson(getIntent().getStringExtra("meeting"), MeetingDTO.class);
        }

        if (getIntent().hasExtra("editmeeting")) {
            myMeeting = gson.fromJson(getIntent().getStringExtra("editmeeting"), MeetingDTO.class);
            agenda = myMeeting.getAgendalist();
            btnPaticipants.setText("Done\nediting");
            UpdateList();
        }


        //Skifter overskrift
        tv.setText("Current meeting: " + myMeeting.getMeetingName());

        //TOPICKNAP
        btnTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Agenda.this, popup_topic_2.class);
                startActivityForResult(intent, 1);

            }
        });

        btnPaticipants.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //GIR MØDET AGENDAEN
                    myMeeting.setAgendalist(agenda);

                    if (getIntent().hasExtra("meeting")) {

                        //MØDE I STRING FORMAT! (taget fra stackoverflow)
                        //-------------------------------------------------
                        Gson gson = new Gson();
                        String myJson = gson.toJson(myMeeting);
                        //-------------------------------------------------

                        Intent intent = new Intent(getApplicationContext(), UserInvited.class);
                        intent.putExtra("mymeeting", myJson);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                        //INDSÆT TIL DATABASEN
//                    DatabaseReference meetingRef = ref.push();
//                    meetingRef.setValue(myMeeting);
                    }

                    if (getIntent().hasExtra("editmeeting")) {

                        database.getReference("Meetings").child(myMeeting.getMeetingID()).child("agendalist").setValue(myMeeting.getAgendalist());

                        //LAVER TOPIC OM TIL ET JSON OBJEKT JEG KAN SENDE MED INTENTET
                        String myJson = gson.toJson(myMeeting);

                        Intent intent = new Intent();
                        intent.putExtra("nymeeting", myJson);
                        setResult(RESULT_OK, intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }




                }
            });

       
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateList();

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
            TopicTime.add(Integer.toString((agenda.get(i).getTopicDuration())/60));
            TopicDescription.add(agenda.get(i).getDescription());
        }

        // DER SKAL LAVES EN NY ADAPTER TIL AT SMIDE DATAEN IND I LISTEN
        listView.setAdapter(new CustomAdapterAgenda(Agenda.this,TopicTitels, TopicTime, TopicDescription));

        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Agenda.this, popup_topic_2.class);
                intent.putExtra("agendatitel",TopicTitels.get(position));
                intent.putExtra("agendatid",TopicTime.get(position));
                intent.putExtra("agendabeskrivelse",TopicDescription.get(position));

                agenda.remove(position);
                startActivityForResult(intent,1);

            }
        });

    }
}




