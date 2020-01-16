package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.smartmeeting.MainLogic.Adapters.CustomAdapterUserInvited;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
import com.example.smartmeeting.MainLogic.DTO.user.UserDTO;
import com.example.smartmeeting.R;
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

        meets = new ArrayList<>();
        userList = new ArrayList<>();

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
                key = meetingRef.getKey();


                for (int i = 0; i < 5; i++) {
                    myMeeting.getInviteUserList().add("bob@peter,dk");
                    myMeeting.getInviteUserList().add("bob@bobsen,dk");
                    myMeeting.getInviteUserList().add("bob@peter,dk");
                    myMeeting.getInviteUserList().add("bob@bobsen,dk");
                    myMeeting.getInviteUserList().add("jens@peter,dk");
                    myMeeting.getInviteUserList().add("simonphilipsen1990@gmail.com");
                    myMeeting.getInviteUserList().add("bob@peter,dk");
                    myMeeting.getInviteUserList().add("tester@tester.dk");
                    myMeeting.getInviteUserList().add("bob@peter,dk");
                    myMeeting.getInviteUserList().add("bob@bobsen,dk");
                }
                inviteUsersToMeeting(myMeeting);


            }
        });

    }

    private void inviteUsersToMeeting(final MeetingDTO meetingToInvFrom)  {

        Thread t = new Thread() {
            public void run() {
                //Henter dataen fra databasen
                for (String emailTIlInvite : meetingToInvFrom.getInviteUserList()) {

                    bob(emailTIlInvite);

                }


                //venter så jeg når at få alt dataen
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                //Smider dem ind i databasen
                for (UserDTO user : userList) {

                    if (user.getMeetingsList() != null) {
                        meets.clear();
                        meets = user.getMeetingsList();
                        meets.add(key);
                        user.setMeetingsList(meets);
                    } else {
                        meets.clear();
                        meets.add(key);
                        user.setMeetingsList(meets);
                    }

                    DatabaseReference ref2 = database.getReference().child("Users").child(user.getEmail().replace(".", ","));

                    ref2.child("meetingsList").setValue(user.getMeetingsList());

                }


                finish();
            }
        };

        t.start();




    }

    public synchronized void bob(final String emailTilInvite){

        final DatabaseReference ref2 = database.getReference().child("Users").child(emailTilInvite.replace(".", ","));


        //EVENT LISTENEREN
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("email")) {
                    UserDTO post = dataSnapshot.getValue(UserDTO.class);
                    userList.add(post);
                    System.out.println("Sådan brugeren er added til 'userList'");
                } else {
                    System.out.println("Brugeren eksitere ikke");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
