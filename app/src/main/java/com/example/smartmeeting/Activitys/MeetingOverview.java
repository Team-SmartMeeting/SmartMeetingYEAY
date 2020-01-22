package com.example.smartmeeting.Activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterMeetings;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public class MeetingOverview extends AppCompatActivity {

    ArrayList<MeetingDTO> meetingsList;

    ArrayList<String> meetingTime;
    ArrayList<String> meetingDate;
    ArrayList<String> meetingTitels;
    ArrayList<String> meetingStartTime;
    ArrayList<String> meetingIDs;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private String email;
    ListView lw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_overview);

        TextView myAwesomeTextView = (TextView)findViewById(R.id.btn_meeting_menu);
        myAwesomeTextView.setText("Meetings");

        Button bigBtn = findViewById(R.id.btn_big);
        bigBtn.setText("Create\n Meeting");

        lw = findViewById(R.id.listview_meetings);

        meetingsList = new ArrayList<>();

        //Arraylist bliver skabt
        meetingTitels = new ArrayList<>();
        meetingStartTime = new ArrayList<>();
        meetingDate = new ArrayList<>();
        meetingIDs = new ArrayList<>();
        meetingTime = new ArrayList<>();



        //KNAPPEN TIL AT OPRETTE MØDER
        bigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateMeeting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //CHECKER OM BRUGEREN ER LOGGET IND, HVIS DER INGEN BRUGER LOGGET IND, LUK APPEN.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        } else {finish();}

        //FINDER EN REFERENCE TIL DEN BRUGER SOM ER LOGGET PÅ MEETINGLIST
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Users").child(email.replace(".",",")).child("meetingsList");

        mReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meetingsList.clear();
                meetingIDs.clear();

                for (DataSnapshot oneSnap : dataSnapshot.getChildren()){

                    //MAPPER DE FORSKELLIGE VÆRDIER!
                    Map<?, ?> value = (Map<?, ?>) oneSnap.getValue();
                    String snap_name = value.get("meetingName").toString();
                    String snap_date = value.get("date").toString();
                    String snap_time = value.get("time").toString();
                    String snap_duration = value.get("duration").toString();

                    //FÅR DURATION I MINUTTER I STEDET FOR SEKUNDER
                    int snap_duration_int = Integer.parseInt(snap_duration);
                    snap_duration_int= snap_duration_int/60;
                    snap_duration = String.valueOf(snap_duration_int);

                    meetingIDs.add(oneSnap.getKey());


                    MeetingDTO hej = new MeetingDTO(snap_name, snap_time, snap_date, Integer.parseInt(snap_duration));

                    meetingsList.add(hej);
                }
                try {
                    updateList();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










        //Menuen
        Button btn_profile = findViewById(R.id.btn_profile_menu);
        Button btn_meetings = findViewById(R.id.btn_meeting_menu);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateList() throws ParseException {

        //CLEAR ALLE ARRAYLISTER SÅ DE ER KLAR TIL AT BLIVE REPOSTET
        meetingTitels.clear();
        meetingTime.clear();
        meetingDate.clear();
        meetingStartTime.clear();

        meetingsList.sort(new Comparator<MeetingDTO>() {
            @Override
            public int compare(MeetingDTO o1, MeetingDTO o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        meetingsList.sort(new Comparator<MeetingDTO>() {
            @Override
            public int compare(MeetingDTO o1, MeetingDTO o2) {
                if (o1.getDate().compareTo(o2.getDate()) == 0) {
                    return o1.getTime().compareTo(o2.getTime());
                }
                return 0;
            }
        });

        //TILFØJER ALLE TOPICS TIL ARRAYLISTER
        for (int i = 0; i < meetingsList.size(); i++) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d = new Date();
            String today = d.getDate() + "/" + (d.getMonth()+1) + "/" + (d.getYear()+1900);

            if (!sdf.parse(meetingsList.get(i).getDate()).before(sdf.parse(today))) {
                    meetingTitels.add(meetingsList.get(i).getMeetingName());
                    meetingTime.add(Integer.toString(meetingsList.get(i).getDuration()));
                    meetingDate.add(meetingsList.get(i).getDate());
                    meetingStartTime.add(meetingsList.get(i).getTime());
            }
        }



        // DER SKAL LAVES EN NY ADAPTER TIL AT SMIDE DATAEN IND I LISTEN
        lw.setAdapter(new CustomAdapterMeetings(MeetingOverview.this,meetingTitels, meetingTime, meetingDate, meetingStartTime, meetingIDs));


        lw.setClickable(true);

        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MeetingOverview.this, StartMeeting.class);
                intent.putExtra("meetingID",meetingIDs.get(position));
                startActivity(intent);

            }
        });

    }
}
