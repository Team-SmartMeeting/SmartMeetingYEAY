package com.example.smartmeeting.Activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class CreateMeeting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Button bigBtn = findViewById(R.id.btn_big);
        bigBtn.setText("Next");


        //Objekter i XML
        final TextView meetingName = findViewById(R.id.create_meeting_name_ed);
        final TextView meetingDate = findViewById(R.id.create_meeting_date_et);
        final TextView meetingTime = findViewById(R.id.create_meeting_time_et);
        final TextView meetingDuration = findViewById(R.id.create_meeting_duration_et);
        final Switch skifter = findViewById(R.id.switch1);

        bigBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                //Checker om felter er tommme!
                if (!meetingName.getText().toString().equals("") && !meetingDuration.getText().toString().equals("") && !meetingDate.getText().toString().equals("") && !meetingTime.getText().toString().equals("")) {

                    boolean ischecked = skifter.isChecked();

                    //opretter et møde
                    MeetingDTO meeting = new MeetingDTO(meetingName.getText().toString(), meetingDate.getText().toString(), meetingTime.getText().toString(), ischecked, Integer.parseInt(meetingDuration.getText().toString()));

                    //MØDE I STRING FORMAT! (taget fra stackoverflow)
                    //-------------------------------------------------
                    Gson gson = new Gson();
                    String myJson = gson.toJson(meeting);
                    //-------------------------------------------------

                    Intent intent = new Intent(getApplicationContext(), Agenda.class);
                    intent.putExtra("meeting", myJson);
                    startActivity(intent);
                    finish();
                }

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
}
