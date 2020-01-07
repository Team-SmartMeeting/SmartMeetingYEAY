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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateMeeting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Button bigBtn = findViewById(R.id.btn_new_meeting);

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


                    //Omformer string til en localdate
                    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(meetingDate.getText().toString(), dateformatter);

                    //Omformer string til localTime
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
                    LocalTime localTime = LocalTime.parse(meetingTime.getText().toString(), timeFormatter);

                    boolean ischecked = skifter.isChecked();

                    //opretter et møde
                    MeetingDTO meeting = new MeetingDTO(meetingName.getText().toString(), localDate, localTime, meetingDuration, )


                    Intent intent = new Intent(getApplicationContext(), Agenda.class);
                    startActivity(intent);
                }

            }
        });
    }
}
