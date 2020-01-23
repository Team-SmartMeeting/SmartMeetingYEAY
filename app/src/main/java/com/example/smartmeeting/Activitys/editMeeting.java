package com.example.smartmeeting.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;

public class editMeeting extends AppCompatActivity {

    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    TextView meetingDate, meetingName, meetingTime, meetingDuration, meetingLokation;

    Gson gson;
    MeetingDTO myMeeting;

    DatabaseReference ref;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);

        //Objekter i XML
        meetingName = findViewById(R.id.create_meeting_name_ed);
        meetingDate = findViewById(R.id.create_meeting_date_et);
        meetingTime = findViewById(R.id.create_meeting_time_et);
        meetingLokation = findViewById(R.id.create_meeting_location_et);
        meetingDuration = findViewById(R.id.create_meeting_duration_et);

        //henter mødet med Gson
        gson = new Gson();
        myMeeting = gson.fromJson(getIntent().getStringExtra("theMeeting"), MeetingDTO.class);


        //FORBINDELSE TIL DATA BASEN
        database = FirebaseDatabase.getInstance();

        //Indsætter hvad der står
        meetingName.setText(myMeeting.getMeetingName());
        meetingDate.setText(myMeeting.getDate());
        meetingDuration.setText(Integer.toString((myMeeting.getDuration())/60));
        meetingLokation.setText(myMeeting.getLokation());
        meetingTime.setText(myMeeting.getTime());


        //DONE EDITING
        Button btn_doneedit = findViewById(R.id.btn_doneEdit);
        btn_doneedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myMeeting.setMeetingName(meetingName.getText().toString());
                myMeeting.setLokation(meetingLokation.getText().toString());
                myMeeting.setTime(meetingTime.getText().toString());
                myMeeting.setDate(meetingDate.getText().toString());
                myMeeting.setDuration((Integer.parseInt(meetingDuration.getText().toString()))*60);

                MeetingDTO meetingOnUserDB = new MeetingDTO(meetingName.getText().toString(), meetingTime.getText().toString(), meetingDate.getText().toString(), (Integer.parseInt(meetingDuration.getText().toString()))*60, meetingLokation.getText().toString());


                if (myMeeting.getInviteUserList().size()<0) {
                    for (String emailTIlInvite : myMeeting.getInviteUserList()) {

                        DatabaseReference ref2 = database.getReference().child("Users").child(emailTIlInvite.replace(".", ",")).child("meetingsList");

                        ref2.child(myMeeting.getMeetingID()).setValue(meetingOnUserDB);

                    }
                }

                DatabaseReference ref2 = database.getReference().child("Users").child(myMeeting.getCreatingUser().replace(".",",")).child("meetingsList");

                ref2.child(myMeeting.getMeetingID()).setValue(meetingOnUserDB);


                database.getReference("Meetings").child(myMeeting.getMeetingID()).setValue(myMeeting);


                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        //EDIT AGENDA
        Button btneditagenda = findViewById(R.id.btn_Editagenda);
        btneditagenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //MØDE I STRING FORMAT! (taget fra stackoverflow)
                //-------------------------------------------------
                String myJson = gson.toJson(myMeeting);
                //-------------------------------------------------

                Intent intent = new Intent(getApplicationContext(), Agenda.class);
                intent.putExtra("editmeeting", myJson);
                startActivityForResult(intent, 420);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        //TID OG DATO HER INDE
        Button btndate = findViewById(R.id.btn_date_et);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TIL DATE PICKER
                c = Calendar.getInstance();
                int days = c.get(Calendar.DAY_OF_MONTH);
                int mounths = c.get(Calendar.MONTH);
                int years = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(editMeeting.this, new DatePickerDialog.OnDateSetListener() {
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

                tpd = new TimePickerDialog(editMeeting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String minuteStri;
                        String hourStri;

                        if (minute < 10) {
                            minuteStri = "0" + minute;
                        } else {
                            minuteStri = Integer.toString(minute);
                        }

                        if (hourOfDay < 10) {
                            hourStri = "0" + hourOfDay;
                        } else {
                            hourStri = Integer.toString(hourOfDay);
                        }

                        meetingTime.setText(hourStri + ":" + minuteStri);
                    }
                }, 12, 00, true);

                tpd.show();

            }
        });

    }

    //taget fra stackoverflow
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 420){
            if (resultCode == RESULT_OK){
                MeetingDTO thenewmeeting = gson.fromJson(data.getStringExtra("nymeeting"), MeetingDTO.class);
                myMeeting = thenewmeeting;
            }

        }
    }


}
