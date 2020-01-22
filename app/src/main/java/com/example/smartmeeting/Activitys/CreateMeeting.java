package com.example.smartmeeting.Activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class CreateMeeting extends AppCompatActivity {

    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    TextView meetingDate, meetingName, meetingTime, meetingDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        Button bigBtn = findViewById(R.id.btn_big);
        bigBtn.setText("Next");
        TextView myAwesomeTextView = (TextView)findViewById(R.id.btn_meeting_menu);
        myAwesomeTextView.setText("Meetings");

        //Objekter i XML
        meetingName = findViewById(R.id.create_meeting_name_ed);
        meetingDate = findViewById(R.id.create_meeting_date_et);
        meetingTime = findViewById(R.id.create_meeting_time_et);
        meetingDuration = findViewById(R.id.create_meeting_duration_et);
        final Switch skifter = findViewById(R.id.switch1);



        bigBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                //Checker om felter er tommme!
                if (!meetingName.getText().toString().equals("") && !meetingDuration.getText().toString().equals("") && !meetingDate.getText().toString().equals("") && !meetingTime.getText().toString().equals("")) {

                    boolean ischecked = skifter.isChecked();

                    //opretter et møde
                    MeetingDTO meeting = new MeetingDTO(meetingName.getText().toString(), meetingDate.getText().toString(), meetingTime.getText().toString(), ischecked, (Integer.parseInt(meetingDuration.getText().toString())*60), 0, 0);

                    //MØDE I STRING FORMAT! (taget fra stackoverflow)
                    //-------------------------------------------------
                    Gson gson = new Gson();
                    String myJson = gson.toJson(meeting);
                    //-------------------------------------------------

                    Intent intent = new Intent(getApplicationContext(), Agenda.class);
                    intent.putExtra("meeting", myJson);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

            }
        });

        Button btndate = findViewById(R.id.btn_date_et);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TIL DATE PICKER
                c = Calendar.getInstance();
                int days = c.get(Calendar.DAY_OF_MONTH);
                int mounths = c.get(Calendar.MONTH);
                int years = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(CreateMeeting.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        meetingDate.setText(mDay+ "/" + (mMonth+1) + "/" + mYear);
                    }
                }, years, mounths, days);
                dpd.show();

            }
        });

        Button btntime = findViewById(R.id.btn_time_et);
        btntime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tpd = new TimePickerDialog(CreateMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        meetingTime.setText(hourOfDay + ":" + minute);
                    }
                }, 12, 00, true);

                tpd.show();

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
                overridePendingTransition(0, 0);
                finish();

            }
        });


        btn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingOverview.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn_meetings.setBackgroundResource(R.drawable.button_pressed);
    }
}
