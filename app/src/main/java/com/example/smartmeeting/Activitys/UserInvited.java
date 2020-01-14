package com.example.smartmeeting.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterAgenda;
import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterUserInvited;
import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.MainLogic.DTO.user.User;
import com.example.smartmeeting.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserInvited extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;

    ListView listView;
    ArrayList<String> emails;

    Gson gson;
    MeetingDTO myMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_invited);

        //HENTER MØDET MED GSON
        gson = new Gson();
        myMeeting = gson.fromJson(getIntent().getStringExtra("mymeeting"), MeetingDTO.class);

        //FORBINDELSE TIL DATA BASEN
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Meetings");

        listView = findViewById(R.id.listview_invited_users);

        emails = new ArrayList<>();

        Button btn_add_user = findViewById(R.id.btn_add_user);
        Button btn_big = findViewById(R.id.btn_big);

        //TILFØJ FLERE USERS (EMAILS)
        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(UserInvited.this, InviteToMeeting.class);
                startActivityForResult(in, 2);
            }
        });

        btn_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i<emails.size(); i++) {
                    emails.set(i, emails.get(i).replace(".",","));
                }

                myMeeting.setInviteUserList(emails);

//        INDSÆT TIL DATABASEN
                DatabaseReference meetingRef = ref.push();
                meetingRef.setValue(myMeeting);

                finish();
            }
        });

    }

    //taget fra stackoverflow
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                String nyUser = data.getStringExtra("nymail2");
                emails.add(nyUser);

                UpdateList();
            }

        }
    }

    public void UpdateList(){

        // DER SKAL LAVES EN NY ADAPTER TIL AT SMIDE DATAEN IND I LISTEN
        listView.setAdapter(new CustomAdapterUserInvited(UserInvited.this, emails));

    }

}
