package com.example.smartmeeting.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterAgenda;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MeetingOverview extends AppCompatActivity {

    ArrayList<MeetingDTO> meetingsList;

    ArrayList<String> meetingTime;
    ArrayList<String> meetingDate;
    ArrayList<String> meetingTitels;
    ArrayList<String> meetingStartTime;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_overview);

        Button bigBtn = findViewById(R.id.btn_big);
        bigBtn.setText("Create\n Meeting");

        bigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateMeeting.class);
                startActivity(intent);

//                Intent intent = new Intent(getApplicationContext(), StartMeeting.class);
//                startActivity(intent);

            }
        });



//        updateList();








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

        btn_meetings.setBackgroundResource(R.drawable.button_pressed);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void updateList() {

        //CLEAR ALLE ARRAYLISTER SÅ DE ER KLAR TIL AT BLIVE REPOSTET
        meetingTitels.clear();
        meetingTime.clear();
        meetingDate.clear();
        meetingStartTime.clear();

        //TILFØJER ALLE TOPICS TIL ARRAYLISTER
        for (int i = 0; i < meetingsList.size(); i++) {
            meetingTitels.add(meetingsList.get(i).getMeetingName());
            meetingTime.add(Integer.toString(meetingsList.get(i).getDuration()));
            meetingDate.add(meetingsList.get(i).getDate());
            meetingStartTime.add(meetingsList.get(i).getTime());
        }



        // DER SKAL LAVES EN NY ADAPTER TIL AT SMIDE DATAEN IND I LISTEN
//        listView.setAdapter(new CustomAdapterAgenda(Agenda.this,meetingTitels, meetingTime, meetingDate, meetingStartTime));


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
