package com.example.smartmeeting.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.smartmeeting.MainLogic.DTO.Topic.Topic;
import com.example.smartmeeting.MainLogic.DTO.meetings.MeetingDTO;
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

/**
 * @author Andreas Østergaard Schliemann
 */

public class StartMeeting extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ArrayList<Topic> topicList;
    private ListView topicListView;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> arrayAdapter;
    private String email;
    private String meetingOwner;
    private String id;
    private Button btnStart;
    private boolean firstTime = true;

    MeetingDTO theMeeting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_meeting);

        topicList = new ArrayList<>();
        listItems = new ArrayList<>();

        btnStart = findViewById(R.id.btn_start);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Tjekker om der er en user logget på
        if (user != null) {
            email = user.getEmail();
        } else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
        email = email.replace(".", ",");

        getMeeting();


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Meetings").child(id);


        //Denne tråd lytter til databasen og aktivere nedenstående metode,
        //hver gang noget data, inden for det område den lytter til, ændre sig.
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                MeetingDTO post = dataSnapshot.getValue(MeetingDTO.class);
                theMeeting = post;

                meetingOwner = post.getCreatingUser();

                if (post.getAgendalist() != null){
                    topicList = post.getAgendalist();
                }

                //Sender bruugeren hen til DuringMeeting, når møderholder starter mødet
                if (firstTime){
                    if (post.getMeetingStatus() == 1){
                        firstTime = false;
                        Intent intent = new Intent(getApplicationContext(), DuringMeeting.class);
                        intent.putExtra("meetingID", id);
                        intent.putExtra("owner", meetingOwner);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                }

                //Sender brugeren hen til EndMeeting, hvis mødet er overstået
                if (post.getMeetingStatus() == 2){
                    Intent intent = new Intent(getApplicationContext(), EndMeeting.class);
                    intent.putExtra("meetingID", id);
                    intent.putExtra("owner", meetingOwner);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }

                final TextView meetingTitle = findViewById(R.id.meetingTitle);
                String meetingTitleString;
                meetingTitleString = post.getMeetingName();
                meetingTitle.setText(meetingTitleString);

                load();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        topicListView = findViewById(R.id.listview_topics);
        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, listItems);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email.replace(".", ",");

                //Det er kun mødeholder, der kan starte mødet
                if (email.equals(meetingOwner)){
                    firstTime = false;

                    mReference.child("meetingStatus").setValue(1);

                    Intent intent = new Intent(getApplicationContext(), DuringMeeting.class);
                    intent.putExtra("meetingID", id);
                    intent.putExtra("owner", meetingOwner);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                else {

                }
            }
        });





        //KNAPPEN TIL AT EDIT MEETING
        Button btneditmeeting = findViewById(R.id.btn_edit_meeting);
        btneditmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MØDE I STRING FORMAT! (taget fra stackoverflow)
                //-------------------------------------------------
                theMeeting.setMeetingID(id);
                Gson gson = new Gson();
                String myJson = gson.toJson(theMeeting);
                //-------------------------------------------------
                Intent intent = new Intent(getApplicationContext(), editMeeting.class);
                intent.putExtra("theMeeting", myJson);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });
    }



    //Opdatere layoutet
    public void load (){

        ProgressBar progressBar = findViewById(R.id.progressBarsm);
        progressBar.setVisibility(View.VISIBLE);

        listItems.clear();

            firstTime = false;
            for (int i = 0;i < topicList.size();i++){
                listItems.add(getTopicTitle(i));
            }

        if (!email.equals(meetingOwner)) {
            btnStart.setBackgroundResource(R.drawable.btn_new_meeting_drawable_disable);
            btnStart.setClickable(false);
            btnStart.setText("Wait");
        }

        topicListView.setAdapter(arrayAdapter);

        arrayAdapter.notifyDataSetChanged();

        progressBar.setVisibility(View.GONE);
    }



    public void getMeeting(){
        id = getIntent().getStringExtra("meetingID");
    }

    public Topic getTopic(int listNum){
        return topicList.get(listNum);
    }

    public String getTopicTitle(int listNum){
        return getTopic(listNum).getTopicName();
    }
}
