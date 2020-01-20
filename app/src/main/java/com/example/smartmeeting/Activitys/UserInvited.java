package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterUserInvited;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.MainLogic.DTO.user.UserDTO;
import com.example.smartmeeting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class UserInvited extends AppCompatActivity {

    DatabaseReference ref;
    FirebaseDatabase database;

    ListView listView;
    ArrayList<String> emails;

    Gson gson;
    MeetingDTO myMeeting;

    String key;
    ArrayList<String> meets;

    UserDTO post;
    ArrayList<UserDTO> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_invited);

        TextView myAwesomeTextView = (TextView)findViewById(R.id.btn_meeting_menu);
        myAwesomeTextView.setText("Meetings");

        meets = new ArrayList<>();
        userList = new ArrayList<>();

        //HENTER MØDET MED GSON
        gson = new Gson();
        myMeeting = gson.fromJson(getIntent().getStringExtra("mymeeting"), MeetingDTO.class);

        //SÆTTER OVERSKRIFT
        TextView tv = findViewById(R.id.metting_name);
        tv.setText("Current meeting: " + myMeeting.getMeetingName());

        //FORBINDELSE TIL DATA BASEN
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Meetings");

        listView = findViewById(R.id.listview_invited_users);

        emails = new ArrayList<>();

        Button btn_add_user = findViewById(R.id.btn_add_user);
        Button btn_big = findViewById(R.id.btn_big);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            myMeeting.setCreatingUser(email.replace(".",","));
        } else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }

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
                key = meetingRef.getKey();
                
                inviteUsersToMeeting(myMeeting);


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


        btn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MeetingOverview.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

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

    private void inviteUsersToMeeting(final MeetingDTO meetingToInvFrom)  {

        MeetingDTO meetingOnUserDB = new MeetingDTO(meetingToInvFrom.getMeetingName(), meetingToInvFrom.getTime(), meetingToInvFrom.getDate(), meetingToInvFrom.getDuration());


        for (String emailTIlInvite : meetingToInvFrom.getInviteUserList()) {

//                    bob(emailTIlInvite);

            DatabaseReference ref2 = database.getReference().child("Users").child(emailTIlInvite.replace(".", ",")).child("meetingsList");
            String key2 = ref2.push().getKey();

            ref2.child(key2).setValue(meetingOnUserDB);

        }

        DatabaseReference ref2 = database.getReference().child("Users").child(meetingToInvFrom.getCreatingUser().replace(".",",")).child("meetingsList");
        String key2 = ref2.push().getKey();

        ref2.child(key2).setValue(meetingOnUserDB);


        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();

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
